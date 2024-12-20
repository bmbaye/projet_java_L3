package com.file_rouge.datas.entities;

import com.file_rouge.datas.enums.Etat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.ToString;



@ToString( exclude = {"client", "role", "etat"})
@Entity
@Table (name = "utilisateur")
public class Utilisateur extends AbstractEntity{

    @Column(name =  "nom", length = 35)
    private String nom;

    @Column(name = "prenom", length = 35)
    private String prenom;

    @Column(unique = true, name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @ManyToOne()
    @JoinColumn(name = "role_id")
    private Role role;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "etat")
    private Etat etat;

    @OneToOne(mappedBy = "utilisateur")
    private Client client;

    public Utilisateur() {
        super();
    }

    public Utilisateur(String nom, String prenom, String login, String password, Role role, Etat etat, Client client) {
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.password = password;
        this.role = role;
        this.etat = etat;
        this.client = client;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
