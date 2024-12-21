package com.file_rouge.datas.entities;
import com.file_rouge.datas.enums.EtatDemande;

public class Demande extends AbstractEntity {
    private Double montant;

    private int client_id;

    private EtatDemande etat;

    private static int count;

    public Demande(Double montant, int client_id, EtatDemande etat) {
        this.montant = montant;
        this.client_id = client_id;
        this.etat = etat;
    }

    public Demande() {
        super();
        count++;
        super.id = count;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public EtatDemande getEtat() {
        return etat;
    }

    public void setEtat(EtatDemande etat) {
        this.etat = etat;
    }
}
