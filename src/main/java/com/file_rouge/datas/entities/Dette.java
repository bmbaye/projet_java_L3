package com.file_rouge.datas.entities;


import com.file_rouge.datas.enums.StatutDette;

public class Dette extends AbstractEntity{

    private Double montant;

    private Double montant_verse;
    
    private Double montant_restant;

    private int client_id;

    private StatutDette statut;

    private static int count;

    public Dette() {
        super();
        count++;
        this.id =count;
    }

    public Dette(Double montant, Double montant_verse, Double montant_restant, int client_id, StatutDette statut) {
        this.montant = montant;
        this.montant_verse = montant_verse;
        this.montant_restant = montant_restant;
        this.client_id = client_id;
        this.statut = statut;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Double getMontant_verse() {
        return montant_verse;
    }

    public void setMontant_verse(Double montant_verse) {
        this.montant_verse = montant_verse;
    }

    public Double getMontant_restant() {
        return montant_restant;
    }

    public void setMontant_restant(Double montant_restant) {
        this.montant_restant = montant_restant;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public StatutDette getStatut() {
        return statut;
    }

    public void setStatut(StatutDette statut) {
        this.statut = statut;
    }
    
}
