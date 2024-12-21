package com.file_rouge.views.impl;

import java.util.List;
import java.util.Scanner;

import com.file_rouge.core.view.impl.ViewImpl;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Role;
import com.file_rouge.datas.entities.Utilisateur;
import com.file_rouge.datas.enums.Etat;
import com.file_rouge.service.UtilisateurService;
import com.file_rouge.service.RoleService;
import com.file_rouge.views.UtilisateurView;

public class UtilisateurViewImpl extends ViewImpl<Utilisateur> implements UtilisateurView{
    
    private UtilisateurService utilisateurService;
    private RoleService roleService;

    public UtilisateurViewImpl(){
        if(this.scan == null){
            this.scan = new Scanner(System.in);
        }
        utilisateurService = utilisateurFactory.getServiceInstence();
        roleService = roleFactory.getServiceInstence();
    }

    @Override
    public Utilisateur saisie(Utilisateur UserConnected) {
       Utilisateur utilisateur = new Utilisateur();

       System.out.println("Le nom:");
       String nom =scan.nextLine();
       System.out.println("Le prenom:");
       String prenom = scan.nextLine();
        String login;
       do {
            System.out.println("le login:");
            login = scan.nextLine();
            if(utilisateurService.selectByLogin(login) !=null){
                System.out.println("Un user porte deja ce login !");
            }
       } while (utilisateurService.selectByLogin(login) !=null);
       System.out.println("Le mot de passe");
       String password = scan.nextLine();
       
       utilisateur.setLogin(login);
       utilisateur.setNom(nom);
       utilisateur.setPrenom(prenom);
       utilisateur.setPassword(password);
       utilisateur.setEtat(Etat.ACTIVE);
       utilisateur.setCreatorUser(UserConnected);
       utilisateur.setUpdateUser(UserConnected);

       return utilisateur;
    }

    @Override
    public Utilisateur saisie(Client client, Role role, Utilisateur userConnected) {
       Utilisateur utilisateur = this.saisie(userConnected);
        utilisateur.setClient_id(client.getId());
        utilisateur.setRole_id(role.getId());

        return utilisateur;
    }


    @Override
    public void display(List<Utilisateur> list) {
        System.out.println("===============================La liste des utilisateurs ============================");
        for (Utilisateur compte : list) {
            System.out.println("ID: "+ compte.getId() + "| Nom: " + compte.getNom() + "| Prenom: "+ compte.getPrenom() +"| Login: " + compte.getLogin());
            System.out.println("-------------------------------------------------");
        }
    }

    @Override
    public Role chooseRole(){
        int rep;
        String[] choices = new String[]{"Client", "Boutiquier", "Admin"};
       do {
            System.out.println("1.Utilisateur client");
            System.out.println("2.Utilisateur boutiquier");
            System.out.println("3.Utilisateur admin");
            rep = scan.nextInt();
       } while (rep <1 || rep>3);
        return roleService.selectByName(choices[rep -1]);
    }

    @Override
    public Utilisateur saisie(Role role, Utilisateur userConnected) {
        Utilisateur utilisateurs = this.saisie(userConnected );
        utilisateurs.setRole_id(role.getId());

        return utilisateurs;
    }

    @Override
    public void display() {
        List<Utilisateur> utilisateurs = this.utilisateurService.findAll();
        this.display(utilisateurs);
    }

    @Override
    public void filtreParEtat(Etat etat) {
        List<Utilisateur> utilisateurs = this.utilisateurService.selectbyEtat(etat);
        this.display(utilisateurs);
    }

    @Override
    public void filtreParRole(Role role) {
        List<Utilisateur> utilisateurs = this.utilisateurService.selectbyRole(role);
        this.display(utilisateurs);
    }

    @Override
    public Etat choosEtat() {
        int rep;
       do {
            System.out.println("1.Active");
            System.out.println("2.Desactive");
            rep = scan.nextInt();
       } while (rep <1 || rep>3);
        return Etat.values()[rep -1];
    }
}
