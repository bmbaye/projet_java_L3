package com.file_rouge.views.users_views;

import java.util.ArrayList;
import java.util.List;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Demande;
import com.file_rouge.datas.entities.Dette;
import com.file_rouge.datas.entities.Utilisateur;
import com.file_rouge.datas.enums.EtatDemande;
import com.file_rouge.datas.enums.StatutDette;
import com.file_rouge.service.ArticleService;
import com.file_rouge.service.ClientService;
import com.file_rouge.service.DemandeService;
import com.file_rouge.service.DetteService;
import com.file_rouge.views.ArticleView;
import com.file_rouge.views.ClientView;
import com.file_rouge.views.DemandeView;
import com.file_rouge.views.DetteView;
import com.file_rouge.views.PaiementView;
import com.file_rouge.views.impl.UserViewImpl;

public class BoutiquierUserView extends UserViewImpl{

    ClientView clientView = clientFactory.getViewInstence();
    ClientService clientService = clientFactory.getServiceInstence();
    DetteView detteView = detteFactory.getViewInstence();
    DetteService detteService = detteFactory.getServiceInstence();
    PaiementView paiementView   = paiementFactory.getViewInstence();
    DemandeView demandeView = demandeFactory.getViewInstence();
    DemandeService demandeService =demandeFactory.getServiceInstence();
    ArticleService articleService = articleFactory.getServiceInstence();
    ArticleView articleView = articleFactory.getViewInstence();

    public BoutiquierUserView(Utilisateur userConnected){
        this.userConnected = userConnected;
    }

    @Override
    public void load(){
        
        int choix;
        do {
            choix =this.showMenu();
            switch (choix) {
                case 1:
                    scan.nextLine();
                    Client client = clientView.saisie(this.userConnected);
                    clientService.create(client);
                    break;
                case 2:
                    clientView.display(clientService.findAll());
                    this.clientSubMenu();
                    break;
                case 3:
                Client cl;
                String tel;
                    do {
                        System.out.println("Entrer le numero du client");
                        tel =scan.nextLine();
                        cl = clientService.selectByTelephone(tel);
                        if(cl ==null){
                            System.out.println("Client non trouve !! Saisir un telelephne valide");
                        }
                    } while (cl ==null);
                    System.out.println(cl.getId());
                    detteView.saisie(cl, this.userConnected);
                    break;
                case 4:
                    List<Dette> dettes =detteService.selectByStatuDette(StatutDette.NON_SOLDE);
                    detteView.display(dettes);
                    this.savePaiement(dettes);
                    break;
                case 5:
                    List<Dette> listDettes =detteService.selectByStatuDette(StatutDette.NON_SOLDE);
                    this.detteSubmenu(listDettes);
                    break;
                case 6:
                    List<Demande> demandes =demandeService.selectByEtat(EtatDemande.ENCOURS);
                    this.demandeSubMenu(demandes);
                    break;
                default:
                    break;
            }
        } while (choix !=7);
    }

    private int showMenu(){
        System.out.println("1.Creer client");
        System.out.println( "2.Lister les clients");
        System.out.println("3.Creer une dette");
        System.out.println("4.Enregistrer paiement");
        System.out.println("5.Lister dette non-soldees");
        System.out.println("6.Lister demandes de dettes");
        System.out.println("7.Deconnexion");
        return scan.nextInt();
    }

    private void clientSubMenu(){
        int rep;
        do {
            System.out.println("1.Avec compte");
            System.out.println("2.Sans compte");
            System.out.println("3.Recherche par telephone");
            System.out.println("4.Retour");
            rep =scan.nextInt();
            switch (rep) {
                case 1:
                    clientView.display(clientService.filterByCompte(1));
                    break;
                case 2:
                    clientView.display(clientService.filterByCompte(0));
                    break;
                case 3:
                    scan.nextLine();
                    System.out.println("Entrez le telephone:");
                    String tel =scan.nextLine();
                    Client client = clientService.selectByTelephone(tel);
                    if(client ==null){
                        System.out.println("Aucun client ne porte ce numero !!");
                    }else{
                        List<Client> list = new ArrayList<>();
                        list.add(client);
                        clientView.display(list);
                        List<Dette> dettes = detteService.selectByClient(client, StatutDette.NON_SOLDE);

                        if(dettes.size() >0){
                            System.out.println("=== Ses dettes ");
                            detteView.display(dettes);
                        }else{
                            System.out.println("Aucune dette");
                            System.out.println("---------------");
                        }
                        List<Demande> demandes = demandeService.selectByEtat(client, EtatDemande.ENCOURS);
                        if(demandes.size() >0){
                            demandeView.display(demandes);
                        }else{
                            System.out.println("Aucune demandes");
                            System.out.println("----------------------");
                        }
                    }
                    break;

                    default:
                        break;
            }
        } while (rep !=4);
    }

    private void detteSubmenu(List<Dette> listDettes){
        int rep;
        do {
            System.out.println("========================= Liste des dettes non soldees ===================");
            detteView.display(listDettes);
            System.out.println("1.Filtrer par client");
            System.out.println("2.Filtrer par etat");
            System.out.println("3.Retour");

            rep = scan.nextInt();

            switch (rep) {
                case 1:
                    scan.nextLine();
                    System.out.println("Entrez le telephone");
                    String tel =scan.nextLine();
                    Client client = clientService.selectByTelephone(tel);
                    if(client == null){
                        System.out.println("Ce client n'existe pas !!");
                    }else{
                        int c;
                        do {
                            List<Dette> dettes = detteService.selectByClient(client, StatutDette.NON_SOLDE);
                            System.out.println("================= dettes du client "+client.getSurname()+" =============");
                            detteView.display(dettes);
                            System.out.println("1.Enregistrer un paiement");
                            System.out.println("2. Filtrer par etat");
                            System.out.println("3. Retour");
                            c = scan.nextInt();
                            switch (c) {
                                case 1:
                                    this.savePaiement(dettes);
                                    break;
                                case 2:
                                    int r;
                                    do {
                                        do {
                                            System.out.println("1.SOLDE");
                                            System.out.println("2.NON SOLDE");
                                            System.out.println("3.Retour");
                                            r=scan.nextInt();
                                            if(r>=1 && r<=3){
                                                if (r !=3) {
                                                    StatutDette statut = StatutDette.values()[r-1];
                                                    List<Dette> dettesFiltrer = detteService.selectByClient(client, statut);
                                                    System.out.println("======Liste des dettes "+statut+" pour le client "+client.getSurname()+"========");
                                                    detteView.display(dettesFiltrer);
                                                }
                                                break;
                                            }
                                        } while (true);
                                    } while (r !=3);
                                    break;
                                default:
                                    break;
                            }
                        } while (c !=3);
                    }
                    break;
                case 2:
                    int r;
                    do {
                        do {
                            System.out.println("1.SOLDE");
                            System.out.println("2.NON SOLDE");
                            System.out.println("3.Retour");
                            r=scan.nextInt();
                            if(r >=1 && r <=3){
                                if (r!=3) {
                                    StatutDette statut =StatutDette.values()[r-1];
                                    List<Dette> dettesFiltrer = detteService.selectByStatuDette(statut);
                                    System.out.println("==================Liste des dettes "+statut+"================");
                                    detteView.display(dettesFiltrer);
                                }
                                break;
                            }
                        } while (true);
                    } while (r!=3);
                    break;
                default:
                    break;
            }
        } while (rep !=3);
    }

    public void savePaiement(List<Dette> dettes){
        int id;
        Dette dette;
        System.out.println("Entrez l'id de la dette:");
        id = scan.nextInt();
        dette = detteService.selectById(id, dettes);
        if(dette !=null){
            if (dette.getMontant() > dette.getMontantVerse()) {
                paiementView.save(this.userConnected, dette);
            }else{
                System.out.println("Cette dette est deja soldee !!");
            }
        }else{
            System.out.println("Dette non trouvee !!");
        }
    }

    public void demandeSubMenu(List<Demande> list){
        
        int rep;
        do {
            demandeView.display(list);
            System.out.println("1.Filtrer par etat");
            System.out.println("2.Voir les articles d'une demande");
            System.out.println("3.Retour");

            rep =scan.nextInt();
            switch (rep) {
                case 1:
                int c;
                    do {
                        System.out.println("1.Encours");
                        System.out.println("2.Accepter");
                        System.out.println("3.Annule");

                        c=scan.nextInt();
                    } while (c<1 || c>3);
                    EtatDemande etat = EtatDemande.values()[c -1];
                    List<Demande> demandes = demandeService.selectByEtat(etat);
                    demandeView.display(demandes);
                    this.detailDemande(demandes);
                    break;
                case 2:
                    this.detailDemande(demandeService.findAll());
                    break;
                default:
                    break;
            }
            demandeService = demandeFactory.getServiceInstence();
            list=demandeService.selectByEtat(EtatDemande.ENCOURS);
        } while (rep !=3);
    }

    private void detailDemande(List<Demande> demandes){
        int c;
        do {
            do {
                System.out.println("1.Traiter une demande");
                System.out.println("2.Retour");
                c =scan.nextInt();
                if(c>=1 && c<=2){
                    if(c ==1){
                        int id;
                        System.out.println("Entrer l'id de la demande");
                        id = scan.nextInt();
                        Demande demande = demandeService.selectById(id,demandes);
                        if(demande !=null){
                            List<Article> articles =articleService.selectByDemande(demande);
                            articleView.display(articles);
                            if(demande.getEtat() == EtatDemande.ENCOURS){
                                demandeView .traiterDemande(demande);
                            }
                        }else{
                            System.out.println("Aucune demande ne porte cette id !!");
                        }
                    }
                    break;
                }
            } while (true);
        } while (c !=2);
       
    }
}
