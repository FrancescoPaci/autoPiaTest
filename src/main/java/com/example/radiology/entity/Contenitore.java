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
    private Integer ordine;

    @ManyToOne
    @JoinColumn(name = "id_organizzazione", nullable = false)
    @JsonIgnoreProperties({"contenitori", "apparecchiatureDirette"})
    @OrderBy("id ASC")
    private Organizzazione organizzazione;

    @Builder.Default
    @OneToMany(mappedBy = "contenitore")
    @JsonIgnoreProperties("contenitore")
    @OrderBy("id ASC")
    private Set<Apparecchiatura> apparecchiature = new LinkedHashSet<>();

}