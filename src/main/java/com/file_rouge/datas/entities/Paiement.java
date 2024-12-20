package com.file_rouge.datas.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "paiement")
public class Paiement extends AbstractEntity {
    
    @Column(name = "montant")
    private Double montant;

    @ManyToOne
    @JoinColumn(name = "dette_id")
    private Dette dette;

    public Paiement() {
    }

    public Paiement(Double montant, Dette dette) {
        super();
        this.montant = montant;
        this.dette = dette;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Dette getDette() {
        return dette;
    }

    public void setDette(Dette dette) {
        this.dette = dette;
    }
}
