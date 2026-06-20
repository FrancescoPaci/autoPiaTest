package com.example.radiology.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(
        name = "contenitore",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_organizzazione_ordine",
                        columnNames = {"id_organizzazione", "ordine"} // 🚀 Garantisce l'unicità della coppia
                )
        }
)
public class Contenitore {

    @Id
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Integer ordine; // Un semplice intero

    @ManyToOne
    @JoinColumn(name = "id_organizzazione", nullable = false)
    @JsonIgnoreProperties({"contenitori", "apparecchiatureDirette"})
    private Organizzazione organizzazione;

    @OneToMany(mappedBy = "contenitore")
    @JsonIgnoreProperties("contenitore")
    private Set<Apparecchiatura> apparecchiature = new LinkedHashSet<>();

    public Contenitore() {}

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

    public Integer getOrdine() {
        return ordine;
    }

    public void setOrdine(Integer ordine) {
        this.ordine = ordine;
    }

    public Organizzazione getOrganizzazione() {
        return organizzazione;
    }

    public void setOrganizzazione(Organizzazione organizzazione) {
        this.organizzazione = organizzazione;
    }

    public Set<Apparecchiatura> getApparecchiature() {
        return apparecchiature;
    }

    public void setApparecchiature(Set<Apparecchiatura> apparecchiature) {
        this.apparecchiature = apparecchiature;
    }
}