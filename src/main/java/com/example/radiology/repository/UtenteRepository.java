package com.example.radiology.repository;

import com.example.radiology.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {

    // 🚀 Questo metodo ti servirà per Spring Security quando farai il Login!
    Optional<Utente> findByUsername(String username);
}