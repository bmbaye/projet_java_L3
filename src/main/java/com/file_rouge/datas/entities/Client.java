package com.file_rouge.datas.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@ToString(callSuper = true)
@EqualsAndHashCode(of = {"surname", "telephone"}, callSuper = false)
@Entity
@Table(name = "client")
public class Client extends AbstractEntity{

    @Column(unique = true, length = 35)
    private String surname;

    @Column(unique = true)
    private String telephone;

    @Column(name =  "adresse", length = 255)
    private String adresse;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = true)
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "client")
    private List<Dette> dettes;

    @OneToMany(mappedBy = "client")
    private List<Demande> demandes;

    public Client() {
        super();
    }
    public Client(String surname, String telephone, String adresse, Utilisateur utilisateur) {
        this.surname = surname;
        this.telephone = telephone;
        this.adresse = adresse;
        this.utilisateur = utilisateur;
    }
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Dette> getDettes() {
        return dettes;
    }

    public void setDettes(List<Dette> dettes) {
        this.dettes = dettes;
    }

}
