package com.example.radiology.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ContenitoreId implements Serializable {

    private Long idOrganizzazione;
    private Integer ordine;

    public ContenitoreId() {}

    public ContenitoreId(Long idOrganizzazione, Integer ordine) {
        this.idOrganizzazione = idOrganizzazione;
        this.ordine = ordine;
    }

    public Long getIdOrganizzazione() { return idOrganizzazione; }
    public void setIdOrganizzazione(Long idOrganizzazione) { this.idOrganizzazione = idOrganizzazione; }

    public Integer getOrdine() { return ordine; }
    public void setOrdine(Integer ordine) { this.ordine = ordine; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContenitoreId that = (ContenitoreId) o;
        return Objects.equals(idOrganizzazione, that.idOrganizzazione) && Objects.equals(ordine, that.ordine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrganizzazione, ordine);
    }
}