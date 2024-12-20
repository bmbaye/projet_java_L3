package com.file_rouge.datas.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(of = {"nom"}, callSuper = false)
@Entity
@Table(name = "role")
public class Role extends AbstractEntity{
    @Column(name =  "nom", unique = true)
    private String nom;

    public Role() {
        super();
    }

    public Role(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
