package com.file_rouge.datas.entities;

import java.util.List;

import com.file_rouge.datas.enums.EtatDemande;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;

@Entity

@EqualsAndHashCode(callSuper = false)
@Table(name = "demande")
public class Demande extends AbstractEntity {

    @OneToMany(mappedBy = "demande", fetch = FetchType.LAZY)
    private List<DemandeArticle> demandeArticles;

    @Column(name = "montant")
    private Double montant;

    @ManyToOne()
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name =  "etat")
    @Enumerated(EnumType.STRING)
    private EtatDemande etat;

    public List<DemandeArticle> getDemandeArticles() {
        return demandeArticles;
    }

    public void setDemandeArticles(DemandeArticle demandeArticles) {
        this.demandeArticles.add(demandeArticles);
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public EtatDemande getEtat() {
        return etat;
    }

    public void setEtat(EtatDemande etat) {
        this.etat = etat;
    }

    public Demande(){
        super();
    }

    public Demande(Double montant, Client client, EtatDemande etat){
        super();
        this.montant = montant;
        this.client = client;
        this.etat = etat;
    }

}
