package com.file_rouge.datas.entities;

public class Paiement extends AbstractEntity {
    
    public Paiement(Double montant, int dette_id) {
        this.montant = montant;
        this.dette_id = dette_id;
    }

    private Double montant;

    private int dette_id;

    private static int count;

    public Paiement() {
        super();
        count++;
        setId(count);
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public int getDette_id() {
        return dette_id;
    }

    public void setDette_id(int dette_id) {
        this.dette_id = dette_id;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Paiement.count = count;
    }

}
