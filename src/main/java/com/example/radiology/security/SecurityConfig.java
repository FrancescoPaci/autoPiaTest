package com.example.radiology.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // 1. 🟢 LIBERA LE RISORSE STATICHE DI ANGULAR (Sintassi corretta per Spring Boot 3)
                        .requestMatchers("/", "/index.html", "/favicon.ico").permitAll()
                        .requestMatchers("/assets/**").permitAll()
                        .requestMatchers("/*.js", "/*.css", "/*.html").permitAll() // File root-level

                        // Se Angular si trova in una sottokartella specifica (es. gestione-apparecchiature) sblocca questa riga:
                        // .requestMatchers("/gestione-apparecchiature/**").permitAll()

                        // Permetti la console di H2 per il debug
                        .requestMatchers("/h2-console/**").permitAll()

                        // 2. 🔑 ENDPOINT PUBBLICI DI AUTENTICAZIONE
                        .requestMatchers("/api/utenti/login", "/login", "/api/utenti/logout").permitAll()

                        // 3. 🔒 PROTEGGI LE API DI BUSINESS
                        // Tutte le chiamate ai controller dei dati richiedono esplicitamente il ruolo ADMIN
                        .requestMatchers("/api/**").hasRole("ADMIN")

                        // 4. Qualsiasi altra richiesta di routing viene lasciata passare (gestita da Angular)
                        .anyRequest().permitAll()
                )

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Usiamo BCrypt per verificare le password cifrate presenti nel tuo data.sql
        return new BCryptPasswordEncoder();
    }
}