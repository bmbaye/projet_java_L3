package com.file_rouge.datas.entities;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(of = {"nom"}, callSuper = false)

public class Role extends AbstractEntity{
    private String nom;
    private static int count;

    public Role() {
        super();
        count++;
        setId(count);
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
