package com.example.radiology.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "utenti")
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String username;
    @Column(nullable = false)
    String password;
    String azienda;
    String ruoli;
    @Column(nullable = false)
    Boolean attivo;
}