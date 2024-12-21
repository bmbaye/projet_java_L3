package com.file_rouge.datas.entities;

import java.util.List;

public class Client extends AbstractEntity{

    private String surname;

    private String telephone;

    private String adresse;

    private int utilisateur_id;
    private static int count;

    public Client() {
        super();
        count++;
        this.id = count;
    }
    public Client(String surname, String telephone, String adresse, int utilisateur_id) {
        this.surname = surname;
        this.telephone = telephone;
        this.adresse = adresse;
        this.utilisateur_id = utilisateur_id;
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
    public int getUtilisateur_id() {
        return utilisateur_id;
    }
    public void setUtilisateur_id(int utilisateur_id) {
        this.utilisateur_id = utilisateur_id;
    }
    public static int getCount() {
        return count;
    }
    public static void setCount(int count) {
        Client.count = count;
    }

}
