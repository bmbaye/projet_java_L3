package com.file_rouge;
import java.util.Scanner;

import com.file_rouge.Factory.UserViewFactory;
import com.file_rouge.core.Factory.impl.UtilisateurFactoryImpl;
import com.file_rouge.datas.entities.Utilisateur;
import com.file_rouge.service.UtilisateurService;
import com.file_rouge.views.users_views.AdminUserView;
import com.file_rouge.views.users_views.BoutiquierUserView;
import com.file_rouge.views.users_views.ClientUserView;



public class Main {
    private static final Scanner scan = new Scanner(System.in);
      
   
    public static void main(String[] args) {
        String login;
        String password;
        Utilisateur userConnected;
        UtilisateurFactoryImpl utilisateurFactory = new UtilisateurFactoryImpl();
        UtilisateurService userService = utilisateurFactory.getServiceInstence();

        do {
            userConnected = null;
            System.out.println("=======Connexion======");
            System.out.println("Entrez le login");
            login = scan.nextLine();
            System.out.println("Entrer le mot de passe");
            password = scan.nextLine();
            userConnected = userService.connexion(login, password);
            if(userConnected == null){
                System.out.println("Utilisateur non trouve !!");
            }else{
                UserViewFactory userViewFactory = new UserViewFactory(userConnected);
        
                if(userConnected.getRole_id() == 1){
                    ClientUserView clientView =userViewFactory.getClientView();
                    clientView.load();
                }else if(userConnected.getRole_id() == 2){
                    BoutiquierUserView boutiquierView = userViewFactory.getBoutiquierView();
                    boutiquierView.load();
        
                }else{
                    AdminUserView adminView = userViewFactory.getAdminUserView();
                adminView.load();
                }
            }
        } while (userConnected != null);
    }
}