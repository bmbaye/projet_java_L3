package com.file_rouge.datas.entities;

public class DemandeArticle extends AbstractEntity{
    private int quantite;

    private int demande_id;

    private int article_id;

    private static int count;

    public DemandeArticle() {
        super();
        count++;
        super.id = count;
    }

    public DemandeArticle(int quantite, int demande_id, int article_id) {
        this.quantite = quantite;
        this.demande_id = demande_id;
        this.article_id = article_id;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getDemande_id() {
        return demande_id;
    }

    public void setDemande_id(int demande_id) {
        this.demande_id = demande_id;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        DemandeArticle.count = count;
    }
    

 
}
