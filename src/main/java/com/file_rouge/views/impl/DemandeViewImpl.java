package com.file_rouge.views.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.file_rouge.core.view.impl.ViewImpl;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Demande;
import com.file_rouge.datas.entities.DemandeArticle;
import com.file_rouge.datas.entities.Detail;
import com.file_rouge.datas.entities.Dette;
import com.file_rouge.datas.entities.Utilisateur;
import com.file_rouge.datas.enums.EtatDemande;
import com.file_rouge.datas.enums.StatutDette;
import com.file_rouge.service.ArticleService;
import com.file_rouge.service.ClientService;
import com.file_rouge.service.DemandeArticleService;
import com.file_rouge.service.DemandeService;
import com.file_rouge.service.DetailService;
import com.file_rouge.service.DetteService;
import com.file_rouge.views.ArticleView;
import com.file_rouge.views.DemandeView;

public class DemandeViewImpl extends ViewImpl<Demande> implements DemandeView  {
    DemandeService demandeService;
    ArticleService articleService;
    ArticleView articleView;
    DemandeArticleService demandeArticleService;
    DetteService detteService;
    DetailService detailService;
    ClientService clientService;

    public DemandeViewImpl(){
        if(this.scan == null){
            this.scan = new Scanner(System.in);
        }
        this.demandeService = this.demandeFactory.getServiceInstence();
        this.articleService = this.articleFactory.getServiceInstence();
        this.articleView = this.articleFactory.getViewInstence();
        this.demandeArticleService = this.demandeArticleFactory.getServiceInstence();
        this.detteService = detteFactory.getServiceInstence();
        this.detailService = detailFactory.getServiceInstence();
        this.clientService = clientFactory.getServiceInstence();
    }
    @Override
    public Demande saisie(Utilisateur userConnected) {
        throw new UnsupportedOperationException("Unimplemented method 'saisie'");
    }

    @Override
    public void display(List<Demande> list) {
       for (Demande demande : list) {
        System.out.println("ID: "+demande.getId()+" |montant: "+demande.getMontant() + " |Demande: "+demande.getCreateAt() +" |Etat: " +demande.getEtat());
        System.out.println("-------------------------------------------------------");
       }
    }

    @Override
    public void display() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'display'");
    }

    @Override
    public void saisie(Client client, Utilisateur userConnected) {

       Demande demande  = new Demande();
       Double montant =0.0;
       String ref = null;
       List<DemandeArticle> details = new ArrayList<>();
       do {
        ref ="";
        Article art =new Article();
        articleView.display(articleService.findAll());
        System.out.println("Pour ajouter un article entrez sa reference [Appuyez sur ENTER si vous avez fini d'ajouter]");
        ref = scan.nextLine();
        if(ref.compareToIgnoreCase("")!=0){
            DemandeArticle detail = new DemandeArticle();
            art.setReference(ref);
            Article article =articleService.selectBy(art);
            if(article != null){
                int qte;
                DemandeArticle d = null;
                for(DemandeArticle det : details){
                    if(det.getArticle_id()== article.getId()){
                        d=det;
                        System.out.println("Cet article est deja choisi. Ajouter une quantite");
                        qte = scan.nextInt();
                        det.setQuantite(qte + det.getQuantite());
                    }
                    break; 
                    }
                    if(d ==null){
                        System.out.println("Entrez une quantite");
                        qte = scan.nextInt();
                        montant = montant +(qte *article.getPrix());
                        detail.setQuantite(qte);
                        detail.setArticle_id(article.getId());
                        detail.setCreatorUser(userConnected);
                        detail.setUpdateUser(userConnected);
                        details.add(detail);
                    }
                }
                scan.nextLine();
            }
        
       } while (ref !="");

       if(details.size() >0){
         demande.setClient_id(client.getId());
         demande.setMontant(montant);
         demande.setEtat(EtatDemande.ENCOURS);
         demande.setCreatorUser(userConnected);
         demande.setUpdateUser(userConnected);

         int demande_id = demande.getId();
         demandeService.create(demande);
         for(DemandeArticle detail: details){
            demande.setId(demande_id);
            detail.setDemande_id(demande_id);
            demandeArticleService.create(detail);
         }
       }
    }
    @Override
    public void traiterDemande(Demande demande) {
        int rep;
        while (true) {
            System.out.println("1.ACCEPTE");
            System.out.println("2.REFUSER");
            System.out.println("3.IGNORER");
            rep = scan.nextInt();
            if (rep>=1 && rep<=3) {
                break;
            }
        }

        if(rep ==1){
            Map<String, Object> errors = this.checkDemande(demande);
            Client client = clientService.selectById(demande.getClient_id());  
            if(errors.size() !=0){
                for (Map.Entry<String, Object> entry : errors.entrySet()) {
                    System.out.println(entry.getValue());
                }
                return;
            }
            demande.setEtat(EtatDemande.ACCEPTEE);
            demandeService.update(demande);
            Dette dette = new Dette();
            dette.setClient_id(client.getId());
            dette.setMontant(demande.getMontant());
            dette.setStatut(StatutDette.NON_SOLDE);
            dette.setMontant_verse(0.0);
            detteService.create(dette);
            List<DemandeArticle> demandeArticles = demandeArticleService.selectByDemande(demande);

            for (DemandeArticle demandeArt : demandeArticles) {
                Article article = articleService.selectById(demandeArt.getArticle_id());
                int qte = article.getQteStock() - demandeArt.getQuantite();
                article.setQteStock(qte);
                articleService.update(article);
                Detail detail = new Detail();
                detail.setArticle_id(article.getId());
                detail.setDette_id(dette.getId());
                Article ar = articleService.selectById(demandeArt.getArticle_id());
                detail.setPrixVente(ar.getPrix());
                detail.setQteVendue(demandeArt.getQuantite());
                detailService.create(detail);
            }
            return;
        }else if(rep ==2){
            demande.setEtat(EtatDemande.ANNULEE);
            demandeService.update(demande);
        }
        return;
    }

    public Map<String, Object> checkDemande(Demande demande){
        Map<String, Object> errors = new HashMap<>();
        List<DemandeArticle> details = demandeArticleService.selectByDemande(demande);
        for (DemandeArticle detail : details) {
            Article art = articleService.selectById(detail.getArticle_id());
            if(detail.getQuantite() > art.getQteStock()){
                errors.put(art.getReference(), "La quantite demandee pour "+art.getReference()+" n'existe pas en stock");
            }
        }
        return errors;
    }

}
