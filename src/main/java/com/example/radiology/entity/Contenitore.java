package com.example.radiology.entity;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "contenitore")
public class Contenitore {

    @EmbeddedId
    private ContenitoreId id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne
    @MapsId("idOrganizzazione")
    @JoinColumn(name = "id_organizzazione")
    private Organizzazione organizzazione;

    // Un contenitore può ospitare molte apparecchiature
    @OneToMany(mappedBy = "contenitore")
    private Set<Apparecchiatura> apparecchiature = new LinkedHashSet<>();

    public Contenitore() {}

    public ContenitoreId getId() { return id; }
    public void setId(ContenitoreId id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Organizzazione getOrganizzazione() { return organizzazione; }
    public void setOrganizzazione(Organizzazione organizzazione) { this.organizzazione = organizzazione; }

    public Set<Apparecchiatura> getApparecchiature() { return apparecchiature; }
    public void setApparecchiature(Set<Apparecchiatura> apparecchiature) { this.apparecchiature = apparecchiature; }
}