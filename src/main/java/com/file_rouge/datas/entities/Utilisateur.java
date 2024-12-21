package com.file_rouge.datas.entities;

import com.file_rouge.datas.enums.Etat;


public class Utilisateur extends AbstractEntity{

    private String nom;

    private String prenom;

    private String login;

    private String password;

    private int role_id;

    private Etat etat;

    private Integer client_id;

    private static int count;

    public Utilisateur(String nom, String prenom, String login, String password, int role_id, Etat etat,
            int client_id) {
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.password = password;
        this.role_id = role_id;
        this.etat = etat;
        this.client_id = client_id;
    }

    public Utilisateur() {
        super();
        count++;
        setId(count);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

}
