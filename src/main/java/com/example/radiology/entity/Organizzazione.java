package com.example.radiology.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "organizzazione")
public class Organizzazione {

    @Id
    private Long id;

    @Column(nullable = false)
    private String nome;

    // Apparecchiature collegate DIRETTAMENTE all'organizzazione (dove id_contenitore è null)
    @OneToMany(mappedBy = "organizzazione")
    @JsonIgnoreProperties("organizzazione")
    @OrderBy("id ASC")
    private Set<Apparecchiatura> apparecchiatureDirette = new LinkedHashSet<>();

    // Contenitori associati a questa organizzazione
    @OneToMany(mappedBy = "organizzazione")
    @JsonIgnoreProperties("organizzazione")
    @OrderBy("ordine ASC")
    private Set<Contenitore> contenitori = new LinkedHashSet<>();

}