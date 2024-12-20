package com.file_rouge.datas.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Table(name = "article")
@Entity
public class Article extends AbstractEntity{

    @Column (name = "reference", length = 10)
    private String reference;

    @Column (name = "libelle", length = 30)
    private String libelle;

    @Column (name = "prix")
    private Double prix;


    @Column(name ="qteStock")
    private int qteStock;

    @OneToMany(mappedBy = "article")
    private List<Detail> details;

    public Article() {
        super();
    }

    public Article(String reference, String libelle, Double prix) {
        super();
        this.reference = reference;
        this.libelle = libelle;
        this.prix = prix;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(Detail detail) {
        this.details.add(detail) ;
    }

    public int getQteStock() {
        return qteStock;
    }

    public void setQteStock(int qteStock) {
        this.qteStock = qteStock;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((reference == null) ? 0 : reference.hashCode());
        result = prime * result + ((libelle == null) ? 0 : libelle.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Article other = (Article) obj;
        if (reference == null) {
            if (other.reference != null)
                return false;
        } else if (!reference.equals(other.reference))
            return false;
        if (libelle == null) {
            if (other.libelle != null)
                return false;
        } else if (!libelle.equals(other.libelle))
            return false;
        return true;
    }
}
