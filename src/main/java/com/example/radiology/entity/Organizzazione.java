package com.example.radiology.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

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
    private Set<Apparecchiatura> apparecchiatureDirette = new LinkedHashSet<>();

    // Contenitori associati a questa organizzazione
    @OneToMany(mappedBy = "organizzazione")
    @JsonIgnoreProperties("organizzazione")
    private Set<Contenitore> contenitori = new LinkedHashSet<>();

    // Costruttori, Getter e Setter
    public Organizzazione() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Apparecchiatura> getApparecchiatureDirette() {
        return apparecchiatureDirette;
    }

    public void setApparecchiatureDirette(Set<Apparecchiatura> apparecchiatureDirette) {
        this.apparecchiatureDirette = apparecchiatureDirette;
    }

    public Set<Contenitore> getContenitori() {
        return contenitori;
    }

    public void setContenitori(Set<Contenitore> contenitori) {
        this.contenitori = contenitori;
    }
}