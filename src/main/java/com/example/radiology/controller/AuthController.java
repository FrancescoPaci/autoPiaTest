package com.example.radiology.controller;

import com.example.radiology.repository.UtenteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password").trim();

        // 1. Cerca l'utente nel DB
        return utenteRepository.findByUsername(username)
                .map(utente -> {
                    // 2. Verifica se l'utente è attivo e se la password inserita coincide con l'hash BCrypt
                    if (utente.getAttivo() && passwordEncoder.matches(password, utente.getPassword().trim())) {

                        // 3. Controlla se ha il ruolo ADMIN
                        if (utente.getRuoli().contains("ROLE_ADMIN")) {
                            // Per ora restituiamo il token demo come richiesto, ma solo se le credenziali sono corrette!
                            return ResponseEntity.ok(Map.of("token", "demo-jwt", "ruolo", utente.getRuoli()));
                        } else {
                            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                    .body(Map.of("error", "Accesso negato: Non hai i privilegi di amministratore."));
                        }
                    }
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(Map.of("error", "Credenziali non valide"));
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Utente non trovato")));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // In un'architettura JWT stateless, basta rispondere con successo.
        // Sarà il frontend Angular a cancellare il token dal browser.
        return ResponseEntity.ok(Map.of("message", "Logout effettuato con successo"));
    }

}
