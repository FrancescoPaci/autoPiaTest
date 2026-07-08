package com.example.radiology.controller;

import com.example.radiology.repository.UtenteRepository;
import com.example.radiology.security.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest, HttpServletResponse response) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        // 1. Cerca l'utente nel DB
        return utenteRepository.findByUsername(username)
                .map(utente -> {

                    //String passwordCriptata = passwordEncoder.encode(password);

                    // 2. Verifica se l'utente è attivo e se la password inserita coincide con l'hash BCrypt
                    if (utente.getAttivo() && passwordEncoder.matches(password, utente.getPassword().trim())) {

                        List<String> roles = java.util.Arrays.stream(utente.getRuoli().split(","))
                                .map(String::trim)
                                .toList();

                        // 🚀 MODIFICA: Generiamo il vero JWT token firmato!
                        String jwtToken = jwtService.generateToken(utente.getUsername(), roles);

                        ResponseCookie cookie = createCookie(jwtToken);

                        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

                        return ResponseEntity.ok(Map.of("roles", roles));
                    }
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(Map.of("error", "Credenziali non valide"));
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Utente non trovato")));
    }

    private ResponseCookie createCookie(String jwtToken) {

        // Se frontend e backend sono su domini/porte diverse (es. localhost:4200 e localhost:8080)
        // è importante impostare SameSite=None (richiede anche Secure=true)
        // Nota: l'oggetto Cookie standard di Java non ha setSameSite,
        // quindi spesso si usa ResponseCookie di Spring:

        return ResponseCookie.from("AUTH-TOKEN", jwtToken)
                .httpOnly(true)
                .secure(false) // impostare a false solo se si testa rigorosamente in HTTP locale senza HTTPS
                .path("/")
                .maxAge(86400)
                .sameSite("Lax") // O "Lax" se sono sullo stesso identico dominio
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {

        // Creiamo un cookie "vuoto" con durata 0
        ResponseCookie deleteCookie = ResponseCookie.from("AUTH-TOKEN", "")
                .httpOnly(true)
                .secure(false) // stesso valore usato nel login
                .path("/")    // stesso path usato nel login
                .maxAge(0)    // <--- QUESTO CANCELLA IL COOKIE IMMEDIATAMENTE
                .sameSite("Lax")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());

        return ResponseEntity.ok(Map.of("msg", "Logout effettuato con successo e cookie rimosso"));
    }

}
