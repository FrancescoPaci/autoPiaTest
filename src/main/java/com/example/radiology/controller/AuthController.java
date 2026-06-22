package com.example.radiology.controller;

import com.example.radiology.repository.UtenteRepository;
import com.example.radiology.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UtenteRepository utenteRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        // 1. Cerca l'utente nel DB
        return utenteRepository.findByUsername(username)
                .map(utente -> {
                    // 2. Verifica se l'utente è attivo e se la password inserita coincide con l'hash BCrypt
                    if (utente.getAttivo() && passwordEncoder.matches(password, utente.getPassword().trim())) {

                        List<String> roles = java.util.Arrays.stream(utente.getRuoli().split(","))
                                .map(String::trim)
                                .toList();

                        // 🚀 MODIFICA: Generiamo il vero JWT token firmato!
                        String realJwtToken = jwtService.generateToken(utente.getUsername(), roles);

                        return ResponseEntity.ok(Map.of(
                                "token", realJwtToken,
                                "roles", roles
                        ));
                    }
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(Map.of("error", "Credenziali non valide"));
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Utente non trovato")));
    }

}
