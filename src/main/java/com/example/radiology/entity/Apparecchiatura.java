package com.example.radiology.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "apparecchiatura")
public class Apparecchiatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String tipologia;

    @Column(name = "numero_serie", nullable = false, unique = true)
    private String numeroSerie;

    @Column(name = "data_installazione", nullable = false)
    private LocalDate dataInstallazione;

    // Opzionale: valorizzato solo se l'apparecchiatura non è in un contenitore
    @ManyToOne
    @JoinColumn(name = "id_organizzazione", nullable = true)
    // 🚀 EVITA LOOP JSON: Impedisce a Jackson di risalire verso l'organizzazione
    @JsonIgnoreProperties({"apparecchiatureDirette", "contenitori"})
    private Organizzazione organizzazione;

    // Opzionale: valorizzato solo se l'apparecchiatura si trova dentro un contenitore.
    // 🚀 NOTA COME DIVENTA SEMPLICE: Ora punta all'ID singolo (Long) del nuovo Contenitore!
    @ManyToOne
    @JoinColumn(name = "id_contenitore", nullable = true)
    // 🚀 EVITA LOOP JSON: Impedisce a Jackson di risalire verso il contenitore (e le altre sue apparecchiature)
    @JsonIgnoreProperties({"apparecchiature", "organizzazione"})
    private Contenitore contenitore;

    // Controllo di integrità prima di salvare nel DB (Rimane perfetto così com'è!)
    @PrePersist
    @PreUpdate
    private void validazioneEsclusivita() {
        if (this.organizzazione != null && this.contenitore != null) {
            throw new IllegalStateException("Un'apparecchiatura non può essere legata contemporaneamente a un'organizzazione e a un contenitore.");
        }
        if (this.organizzazione == null && this.contenitore == null) {
            throw new IllegalStateException("L'apparecchiatura deve essere associata a un'organizzazione o a un contenitore.");
        }
    }

    // Costruttori
    public Apparecchiatura() {}

    // Getter e Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTipologia() { return tipologia; }
    public void setTipologia(String tipologia) { this.tipologia = tipologia; }

    public String getNumeroSerie() { return numeroSerie; }
    public void setNumeroSerie(String numeroSerie) { this.numeroSerie = numeroSerie; }

    public LocalDate getDataInstallazione() { return dataInstallazione; }
    public void setDataInstallazione(LocalDate dataInstallazione) { this.dataInstallazione = dataInstallazione; }

    public Organizzazione getOrganizzazione() { return organizzazione; }
    public void setOrganizzazione(Organizzazione organizzazione) { this.organizzazione = organizzazione; }

    public Contenitore getContenitore() { return contenitore; }
    public void setContenitore(Contenitore contenitore) { this.contenitore = contenitore; }
}