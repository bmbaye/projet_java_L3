package com.file_rouge.datas.entities;

import java.util.List;

import com.file_rouge.datas.enums.StatutDette;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity

@ToString( exclude = {"client"})
@EqualsAndHashCode(callSuper = false)
@Table(name = "dette")
public class Dette extends AbstractEntity{

    @Column(name = "montant")
    private Double montant;

    @Column(name = "montant_verse")
    private Double montantVerse;
    
    @Transient
    private Double montantRestant;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "Statut")
    private StatutDette statut;

    @OneToMany(mappedBy = "dette")
    private List<Paiement> paiements;

    @OneToMany(mappedBy = "dette", fetch = FetchType.LAZY)
    private List<Detail> details;

    public Dette(Double montant, Double montantVerse, Double montantRestant, Client client) {
        super();
        this.montant = montant;
        this.montantVerse = montantVerse;
        this.montantRestant = montantRestant;
        this.client = client;
    }

    public Dette() {
        super();
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Double getMontantVerse() {
        return montantVerse;
    }

    public void setMontantVerse(Double montantVerse) {
        this.montantVerse = montantVerse;
    }

    public Double getMontantRestant() {
        return montantRestant;
    }

    public void setMontantRestant(Double montantRestant) {
        this.montantRestant = montantRestant;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(Detail detail) {
        this.details.add(detail);
    }

    public StatutDette getStatut() {
        return statut;
    }

    public void setStatut(StatutDette statut) {
        this.statut = statut;
    }

    public List<Paiement> getPaiements() {
        return paiements;
    }
    
}
