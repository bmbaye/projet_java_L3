package com.file_rouge.views.users_views;

import java.util.List;

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
import com.file_rouge.views.DemandeView;
import com.file_rouge.views.DetteView;
import com.file_rouge.views.impl.UserViewImpl;

public class ClientUserView  extends UserViewImpl{

    private Client client;

    DetteView detteView = detteFactory.getViewInstence();
    DetteService detteService = detteFactory.getServiceInstence();
    DemandeView demandeView = demandeFactory.getViewInstence();
    DemandeService demandeService = demandeFactory.getServiceInstence();
    ArticleView articleView = articleFactory.getViewInstence();
    ArticleService articleService = articleFactory.getServiceInstence();  
    ClientService clientService = clientFactory.getServiceInstence(); 

    public ClientUserView(Utilisateur userConnected){
        this.userConnected = userConnected;
        this.client = clientService.selectByUserId(userConnected.getId());
    }
    
    public void load(){
        int choix;
        do {
            System.out.println("1.Lister dettes");
            System.out.println("2.Faire une demande de dette");
            System.out.println("3.Lister ses demandes");
            System.out.println("4.Deconnexion");
            choix = scan.nextInt();

            switch (choix) {
                case 1:
                    System.out.println(this.client.getId());
                    List<Dette> dettes = detteService.selectByClient(this.client, StatutDette.NON_SOLDE);
                    this.detteSubmenu(dettes);
                    break;
                case 2:
                    demandeView.saisie(this.client, this.userConnected);
                case 3:
                    List<Demande> demandes = demandeService.selectByEtat(this.client, EtatDemande.ENCOURS);
                    this.demandeSubmenu(demandes);
                default:
                    break;
            }
        } while (choix !=4);
    }

    private void detteSubmenu(List<Dette> listDettes){
        int choix;
        do {
            System.out.println("================== Les dettes non soldees =================");
            detteView.display(listDettes);
            System.out.println("1.Voir les articles d'une dette");
            System.out.println("2.Voir les paiements d'une dette");
            System.out.println("3.Retour");
            choix=scan.nextInt();

            switch (choix) {
                case 1:
                    int c;
                    do {
                        System.out.println("Entrer l'id de la dette");
                        int id = scan.nextInt();
                        List<Dette> dettes = detteService.selectAll();
                        Dette dette = detteService.selectById(id, dettes);
                        if(dette ==null){
                            System.out.println("Aucune dette ne porte cette id");
                            break;
                        }else{
                            System.out.println("======= Les article de la dette =========");
                            articleView.display(articleService.selectByDette(dette));
                        }
                        System.out.println("0.Retour");
                        c=scan.nextInt();
                        if(c ==0){
                            break;
                        }
                    } while (true);
                    break;
                case 2:
                System.out.println("Entrer l'id de la dette");
                int id2 = scan.nextInt();
                Dette dette2 = detteService.selectById(id2);
                if(dette2 ==null){
                    System.out.println("Aucune dette ne porte cette id");
                }else{
                    System.out.println("========== Les paiements de la dette ========");
                    detteView.showPaiements(dette2);
                }
                    break;
                default:
                    break;
            }
        } while (choix !=3);
    }

    public void demandeSubmenu(List<Demande> demandes){
        int rep;
        do {
            System.out.println("===================== La liste des demandes ================");
            demandeView.display(demandes);
            System.out.println("1.Filtrer par etat");
            System.out.println("2.Relancer une demande refusee");
            System.out.println("3.Retour");

            rep = scan.nextInt();

            switch (rep) {
                case 1:
                    int r;
                    do {
                        do {
                            System.out.println("1.Encours");
                            System.out.println("2.Acceptee");
                            System.out.println("3.Annulee");
                            System.out.println("4.Retour");
                            r=scan.nextInt();

                            if(r>=1 && r<=4){
                                if (r !=4) {
                                    EtatDemande etat = EtatDemande.values()[r-1];
                                    List<Demande> list = demandeService.selectByEtat(client, etat);
                                    System.out.println("======== Les demandes "+etat+" ==========");
                                    demandeView.display(list);
                                }
                                break; 
                            }
                        } while (true);
                    } while (r !=4);
                    break;
                case 2:
                    List<Demande> demandeRefusees = demandeService.selectByEtat(client, EtatDemande.ANNULEE);
                    if(demandeRefusees.size() >0){
                        System.out.println("Entrez l'id de la dette");
                        int id = scan.nextInt();
                        Demande demande = demandeRefusees.stream().filter(d-> d.getId() == id).findFirst().orElse(null);
                        if(demande !=null){
                            demande.setEtat(EtatDemande.ACCEPTEE);
                            demandeService.update(demande);
                            System.out.println("Demande relancee");
                        }else{
                            System.out.println("Aucune demande ne correspond a cet id");
                        }
                    }
                    break;                    
                default:
                    break;
            }
            demandeService = demandeFactory.getServiceInstence();
            demandes = demandeService.selectByEtat(this.client, EtatDemande.ENCOURS);
        } while (rep !=3);
    }

}
