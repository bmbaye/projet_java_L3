package com.file_rouge.datas.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;



@Entity
@Table(name = "detail")
@EqualsAndHashCode(of = {"dette", "article"}, callSuper = false)
public class Detail extends AbstractEntity{

    @Column(name =  "prix_vente")
    private Double prixVente;

    @Column(name = "qte_vendue")
    private Integer qteVendue;
    
    @ManyToOne
    @JoinColumn(name = "dette_id")
    private Dette dette;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    public Detail(Double prixVente, Integer qteVendue, Dette dette, Article article) {
        super();
        this.prixVente = prixVente;
        this.qteVendue = qteVendue;
        this.dette = dette;
        this.article = article;
    }

    public Detail() {
        super();
    }

    public Double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(Double prixVente) {
        this.prixVente = prixVente;
    }

    public Integer getQteVendue() {
        return qteVendue;
    }

    public void setQteVendue(Integer qteVendue) {
        this.qteVendue = qteVendue;
    }

    public Dette getDette() {
        return dette;
    }

    public void setDette(Dette dette) {
        this.dette = dette;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    
}
