package com.example.radiology.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Abilita la configurazione CORS impostata in WebConfig
                .cors(cors -> cors.configure(http))
                // 2. Disabilita il CSRF (obbligatorio per le API REST)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Niente sessioni HTTP
                .authorizeHttpRequests(auth -> auth
                        // 🚀 REGOLA FONDAMENTALE: Accetta tutte le richieste OPTIONS di preflight del browser
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Tutte le tue altre regole esistenti...
                        .requestMatchers("/", "/index.html", "/favicon.ico", "/assets/**").permitAll()
                        .requestMatchers("/*.js", "/*.css", "/*.html").permitAll()
                        .requestMatchers("/api/utenti/login", "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                );
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Usiamo BCrypt per verificare le password cifrate presenti nel tuo data.sql
        return new BCryptPasswordEncoder();
    }
}