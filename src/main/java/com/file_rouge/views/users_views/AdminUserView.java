package com.file_rouge.views.users_views;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Role;
import com.file_rouge.datas.entities.Utilisateur;
import com.file_rouge.datas.enums.Etat;
import com.file_rouge.service.ArticleService;
import com.file_rouge.service.ClientService;
import com.file_rouge.service.RoleService;
import com.file_rouge.service.UtilisateurService;
import com.file_rouge.views.ArticleView;
import com.file_rouge.views.ClientView;
import com.file_rouge.views.UtilisateurView;
import com.file_rouge.views.impl.UserViewImpl;

public class AdminUserView extends UserViewImpl{

    UtilisateurView userView= userFactory.getViewInstence();
    UtilisateurService userService = userFactory.getServiceInstence();
    RoleService roleService = roleFactory.getServiceInstence();
    ClientService clientService = clientFactory.getServiceInstence();
    ClientView clientView = clientFactory.getViewInstence();
    ArticleView articeView = articleFactory.getViewInstence();
    ArticleService articleService = articleFactory.getServiceInstence();

    public AdminUserView() {
	}

	public AdminUserView(Utilisateur userConnected){
        this.userConnected = userConnected;
    }

    @Override
    public void load() {
       int choix;
       do {
            System.out.println("1.Comptes");
            System.out.println("2.Les articles");
            System.out.println("3.Deconnexion");

            choix = scan.nextInt();
            switch (choix) {
                case 1:
                    this.compteSubmenu();
                    break;
                case 2:
                    this.articleSubmenu();
                    break;
                case 3:
                    break;
            
                default:
                    break;
            }
        
       } while (choix !=3);
    }

    private void compteSubmenu(){
        int choix;
        do {
            System.out.println("1.Creer un compte");
            System.out.println("2.Creer un compte a un client");
            System.out.println("3.Desactiver un compte");
            System.out.println("4.Filtrer par role");
            System.out.println("5.Filtrer par Etat");
            System.out.println("6.Retour");
            choix = scan.nextInt();

            switch (choix) {
                case 1:
                int c;
                    do {
                        Role role;
                        do {
                            System.out.println( "Role ?");
                            System.out.println("1.Boutiquier");
                            System.out.println("2.Admin");
                            System.out.println("3.Annuler");
                            c= scan.nextInt();
                            if(c>=1 && c<=3){
                                if (c != 3) {
                                    Utilisateur user = userView.saisie(this.userConnected);
                                    if(c ==1){
                                        role = roleService.selectByName("BOUTIQUIER");
                                        user.setRole(role);
                                    }else if(c ==2){
                                        role = roleService.selectByName("ADMIN");
                                        user.setRole(role);
                                    }
                                    userService.create(user);
                                }
                                break;
                            }
                        } while (true);
                        break;
                    } while (c !=3);
                    break;
                case 2:
                    List<Client> clients = clientService.filterByCompte(0);
                    if (clients.size() > 0) {
                        clientView.display(clients);
                        String tel;
                        Client cl;
                        do{
                            System.out.println("Entrer le telephone du client");
                            tel =scan.nextLine();
                            cl = clientService.selectByTelephone(tel, clients);
                            if(cl !=null){
                                Role role = roleService.selectByName("CLIENT");
                                Utilisateur user =userView.saisie(cl, role, this.userConnected);
                                int id_user = userService.create(user);
                                Utilisateur utilisateur = new Utilisateur();
                                utilisateur.setId(id_user);
                                cl.setUtilisateur(utilisateur);
                                clientService.modifier(cl);
                                break;
                            }
    
                        }while(true);
                    }else{
                        System.out.println("Tous les clients ont des comptes !!");
                    }
                    break;
                case 3:
                    List<Utilisateur> users =userService.selectbyEtat(Etat.ACTIVE);
                    int id;
                    do {
                        userView.display(users);
                        System.out.println("Entrer l'id du compte");
                        id =scan.nextInt();
                        int user_id = id;
                        Utilisateur user = users.stream().filter(u ->u.getId() ==user_id).findFirst().orElse(null);
                        if(user !=null){
                            System.out.println("Voulez vous  vraiment desactiver ce compte ? (o/n)");
                            String r;
                            do {
                                r =scan.nextLine();
                                if(r.compareToIgnoreCase("o") == 0 || r.compareToIgnoreCase("n") == 0){
                                    break;
                                }
                            } while (true);
                            if(r.compareToIgnoreCase("o") == 0){
                                user.setEtat(Etat.DESACTIVE);
                                userService.modifier(user);
                                System.out.println("Compte desactive !!");
                            }
                            break;
                        }
                    } while (true);
                    break;
            
                case 4:
                    List<Role> roles = roleService.findAll();
                    for (Role role : roles) {
                        System.out.println("id : " + role.getId() + " |Nom : "+role.getNom());
                    }
                    int idRole;
                    do {
                        System.out.println("Entrer l'id du role");
                        idRole = scan.nextInt();
                        Role role = roleService.selectById(idRole);
                        if(role!=null){
                            List<Utilisateur> usersByRole = userService.selectbyRole(role);
                            userView.display(usersByRole);
                            break;
                        }
                    } while (true);
                    break;
                case 5:
                int numEtat;
                do {
                    System.out.println("1.ACTIVE");
                    System.out.println("2.DESACTIVE");
                    numEtat = scan.nextInt();
                    if(numEtat ==1 || numEtat == 2){
                        List<Utilisateur> usersByEtat = userService.selectbyEtat(Etat.values()[numEtat-1]);
                        userView.display(usersByEtat);
                        break;
                    }
                } while (true);
                break;
                default:
                    break;
            }
        } while (choix !=6);
    }

    private void articleSubmenu(){
        int choix;
        do {
            System.out.println("1.Articles disponibles");
            System.out.println("2.Articles en rupture");
            System.out.println("3.Mettre a jour un article");
            System.out.println("4.Retour");
            choix = scan.nextInt();

            switch (choix) {
                case 1:
                    List<Article> listDispo = articleService.selectByDisponibilite(1);
                    articeView.display(listDispo);
                    break;
                case 2:
                    List<Article> listRupture = articleService.selectByDisponibilite(0);
                    articeView.display(listRupture);
                    break;
                case 3:
                    int id;
                    do {
                        List<Article> list = articleService.findAll();
                        articeView.display(list);
                        System.out.println("Entrer l'id de l'article");
                        id = scan.nextInt();    
                        Article article = articleService.selectById(id);
                        if(article!=null){
                            int qte;
                            do {
                                System.out.println("Entrer la nouvelle quantite");
                                qte =scan.nextInt();
                                if(qte >0){
                                    break;
                                }
                            } while (true);

                            article .setQteStock(qte);
                            articleService.update(article);
                            System.out.println("Article mis a jours avec succes !");
                            break;
                        }
                        
                    } while (true);
                    break;
                default:
                    break;
            }
        }while(choix !=4);

    }

    
}
