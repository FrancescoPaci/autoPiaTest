package com.example.radiology.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Abilita la configurazione CORS impostata in WebConfig
                .cors(cors -> cors.configure(http))

                // 2. Disabilita il CSRF (obbligatorio per le API REST)
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // 🚀 REGOLA FONDAMENTALE: Accetta tutte le richieste OPTIONS di preflight del browser
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Tutte le tue altre regole esistenti...
                        .requestMatchers("/", "/index.html", "/favicon.ico", "/assets/**").permitAll()
                        .requestMatchers("/*.js", "/*.css", "/*.html").permitAll()
                        .requestMatchers("/api/utenti/login", "/auth/login").permitAll()

                       // .requestMatchers("/api/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                );

        return http.build();
    }
/*
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        // 1. Abilita la configurazione CORS (legge il WebConfig o i Bean CORS)
        .cors(cors -> cors.configure(http))

        // 2. Disabilita il CSRF (inutile e dannoso se si usano le API REST Stateless con JWT)
        .csrf(csrf -> csrf.disable())

        // 3. 🚀 FONDAMENTALE: Forza Spring Security a non creare MAI sessioni in memoria
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // 4. Regole di autorizzazione per le rotte
        .authorizeHttpRequests(auth -> auth
            // Sblocca il preflight del browser (OPTIONS) per evitare i 403 automatici prima delle POST
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

            // Rotte pubbliche (Login, asset statici se presenti)
            .requestMatchers("/auth/login", "/api/utenti/login").permitAll()
            .requestMatchers("/", "/index.html", "/favicon.ico", "/assets/**").permitAll()
            .requestMatchers("/*.js", "/*.css", "/*.html").permitAll()

            // Rotte protette
           // .requestMatchers("/api/**").hasRole("ADMIN")

            // Qualsiasi altra richiesta deve essere autenticata
            .anyRequest().authenticated()
        )

        // 5. Inserisci il filtro mock (o il filtro JWT reale) prima del controllo di autenticazione standard
        .addFilterBefore(tokenMockFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

    return http.build();
}
*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Usiamo BCrypt per verificare le password cifrate presenti nel tuo data.sql
        return new BCryptPasswordEncoder();
    }
}