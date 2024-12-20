package com.file_rouge.views.impl;

import java.util.List;
import java.util.Scanner;

import com.file_rouge.core.view.impl.ViewImpl;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Role;
import com.file_rouge.datas.entities.Utilisateur;
import com.file_rouge.datas.enums.Etat;
import com.file_rouge.service.ClientService;
import com.file_rouge.service.RoleService;
import com.file_rouge.views.ClientView;
import com.file_rouge.views.UtilisateurView;

public class ClientViewImpl extends ViewImpl<Client> implements ClientView{

    private ClientService clientService;
    private UtilisateurView utilisateurView;
    private RoleService roleService;

    public ClientViewImpl(){
        if(this.scan == null){
            this.scan = new Scanner(System.in);
        }
        clientService = clientFactory.getServiceInstence();
        utilisateurView = utilisateurFactory.getViewInstence();
        roleService = roleFactory.getServiceInstence();
    }

    @Override
    public Client saisie(Utilisateur userConnected) {
        Client client = new Client();

        String surname;
        Client cl;
        do {
            System.out.println("Le surname:");
            surname = scan.nextLine();
            cl =clientService.selectBySurname(surname);
        } while (cl !=null || surname =="");

        String telephone;
        do {
            System.out.println("Le telephone:");
            telephone = scan.nextLine();
        } while (clientService.selectByTelephone(telephone) !=null);
        System.out.println("adresse ?");
        String adresse = scan.nextLine();
        int rep;
        do {
            System.out.println( "Creer un compte ?");
            System.out.println("1.OUI");
            System.out.println("2.NON");
            rep = scan.nextInt();
        } while (rep !=1 && rep !=2);
        Utilisateur compte = new Utilisateur();
        if(rep ==1){
            Utilisateur user =utilisateurView.saisie(userConnected);
            Role role = roleService.selectByName("CLIENT");
            System.out.println(role.getId());
            System.out.println(role.getNom());
            compte.setRole(role);
            compte.setClient(client);
            compte.setNom(user.getNom());
            compte.setPrenom(user.getPrenom());
            compte.setLogin(user.getLogin());
            compte.setPassword(user.getPassword());
            compte.setEtat(Etat.ACTIVE);
            compte.setCreatorUser(userConnected);
            compte.setUpdateUser(userConnected);
        }

        client.setSurname(surname);
        client.setTelephone(telephone);
        client.setAdresse(adresse);
        client.setUtilisateur(compte);
        client.setCreatorUser(userConnected);
        client.setUpdateUser(userConnected);

        return client;
    }

    @Override
    public void display(List<Client> list) {
        for (Client client : list) {
            System.out.println("ID: "+client.getId()+ " |Surname: " + client.getSurname()+ " |Telephone: " + client.getTelephone() + " |Adresse: " + client.getAdresse());
        }
    }

    @Override   
    public void display() {
        List<Client> clients =this.clientService.findAll();
        this.display(clients);
    }
    
}
  