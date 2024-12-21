package com.file_rouge.datas.entities;

public class Detail extends AbstractEntity{

    private Double prixVente;

    private int qteVendue;
    
    private int dette_id;

    private int article_id;

    private static int count;

    public Detail(Double prixVente, int qteVendue, int dette_id, int article_id) {
        this.prixVente = prixVente;
        this.qteVendue = qteVendue;
        this.dette_id = dette_id;
        this.article_id = article_id;
    }



    public Detail() {
        super();
        count++;
        super.id = count;
    }



    public Double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(Double prixVente) {
        this.prixVente = prixVente;
    }

    public int getQteVendue() {
        return qteVendue;
    }

    public void setQteVendue(int qteVendue) {
        this.qteVendue = qteVendue;
    }

    public int getDette_id() {
        return dette_id;
    }

    public void setDette_id(int dette_id) {
        this.dette_id = dette_id;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }
    
}
