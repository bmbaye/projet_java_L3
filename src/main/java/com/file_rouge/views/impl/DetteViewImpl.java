package com.file_rouge.views.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.file_rouge.core.view.impl.ViewImpl;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Detail;
import com.file_rouge.datas.entities.Dette;
import com.file_rouge.datas.entities.Paiement;
import com.file_rouge.datas.entities.Utilisateur;
import com.file_rouge.datas.enums.StatutDette;
import com.file_rouge.service.ArticleService;
import com.file_rouge.service.DetailService;
import com.file_rouge.service.DetteService;
import com.file_rouge.service.PaiementService;
import com.file_rouge.views.ArticleView;
import com.file_rouge.views.DetteView;
import com.file_rouge.views.PaiementView;

public class DetteViewImpl extends ViewImpl<Dette> implements DetteView {
    DetteService detteService;
    ArticleService articleService;
    ArticleView articleView;
    DetailService detailService;
    PaiementView paiementView;
    PaiementService paiementService;

    public DetteViewImpl(){
        if(this.scan == null){
            this.scan = new Scanner(System.in);
        }
        this.detteService = detteFactory.getServiceInstence();
        this.articleService = this.articleFactory.getServiceInstence();
        this.articleView  =this.articleFactory.getViewInstence();
        this.detailService = this.detailFactory.getServiceInstence();
        this.paiementView = this.paiementFactory.getViewInstence();
        this.paiementService = this.paiementFactory.getServiceInstence();
    }
    @Override
    public Dette saisie(Utilisateur userConnected) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'display'");
    }

    @Override
    public void display(List<Dette> list) {
        for (Dette dette : list) {
            System.out.println("ID: "+dette.getId()+ " |montant: " + dette.getMontant() + " |montant verse: "+ dette.getMontant_verse() + " |date: "+dette.getCreateAt() + " |Tel. CLient: ");
        }
    }

    @Override
    public void display() {
        
    }
    @Override
    public void saisie(Client client, Utilisateur userConnected) {
        Dette dette = new Dette();
        dette.setClient_id(client.getId());
        Double montant =0.0;
        String ref =null;
        List<Detail> details = new ArrayList<>();
        do {
            Article art = new Article();
            ref ="";
            articleView.display(articleService.findAll());
            System.out.println("Pour ajouter un article entrez sa reference [Appuyez sur ENTER si vous avez fini d'ajouter]");
            ref =scan.nextLine();
         if(ref.compareTo("")!=0){
            Detail detail = new Detail();
            art.setReference(ref);
             Article article = articleService.selectBy(art);
             article.setCreatorUser(userConnected);
             article.setUpdateUser(userConnected);
             if(article !=null){
                 int qte;
                 Double prix =0.0;
                 Detail d =null;
                 for (Detail det : details) {
                    Article ar = articleService.selectById(det.getArticle_id());
                    if(ar.equals(article)){
                        d=det;
                        qte = this.saisieQte(article, "Cet article est deja choisi. Ajouter une quantite");
                        det.setQteVendue(det.getQteVendue() + qte);
                        montant = montant + (qte * det.getPrixVente());
                    }
                    break;
                 }

                if (d ==null) {
                    System.out.println(article.getQteStock());
                    qte = this.saisieQte(article, "Entrez une quantite");
                    do {
                        System.out.println("Entrez le prix de vente [mettre 0 si c'est le prix de l'article]");
                        prix = scan.nextDouble();
                        if( prix <= 0){
                            System.out.println("Entrer un prix valid");
                        }
                    } while ( prix <= 0 && prix !=0.0);
                    if(prix ==0){
                        prix = article.getPrix();
                    }
                    montant = montant + ( prix * qte);
                    detail.setPrixVente(prix);
                    detail.setArticle_id(article.getId());
                    detail.setQteVendue(qte);
                    details.add(detail);
                }
             }
             scan.nextLine();
         }
        } while (ref !="");
        
       if (details.size() >0) {
        dette.setMontant(montant);
        dette.setCreatorUser(userConnected); //
        dette.setUpdateUser(userConnected); //
        Double montant_verse;
        do {
        System.out.println("Le montant verse");
        montant_verse =scan.nextDouble();
        } while (montant_verse <0 || montant_verse > montant);
        Double montant_restant = montant - montant_verse;
        StatutDette statut;
        if(montant_restant ==0){
        statut = StatutDette.SOLDE;
        }else{
        statut = StatutDette.NON_SOLDE;
        }
        dette.setMontant_verse(montant_verse);
        dette.setMontant_restant(montant_restant);
        dette.setStatut(statut);
        detteService.create(dette);
        int dette_id = dette.getId();
        dette.setId(dette_id);
        for (Detail detail : details) {
            Article article =articleService.selectById(detail.getArticle_id());
            article.setPrix(detail.getPrixVente());
            int qte = article.getQteStock() - detail.getQteVendue();
            article.setQteStock(qte);
            articleService.update(article);
            detail.setDette_id(dette.getId());
            detail.setCreatorUser(userConnected);
            detail.setCreateAt(dette.getCreateAt());
            detail.setUpdateUser(userConnected);
            detailService.create(detail);
        }
       }

       scan.nextLine();
    }
    @Override
    public int saisieQte(Article article, String msg) {
        int qte;
        do {
            System.out.println(msg);
            qte = scan.nextInt();
            if( qte > article.getQteStock()){
                System.out.println("Nous n'avons pas cette quantite en stock !");
            }
        } while (qte<=0 || qte > article.getQteStock());
        return qte;
    }
    @Override
    public void showArticles(Dette dette) {
        List<Detail> details = detailService.selectByDette(dette);
        for (Detail detail : details) {
            Article article = articleService.selectById(detail.getArticle_id());
            System.out.println("ID: "+ article.getId()+" |Libelle: "+article.getLibelle()+" |References: "+article.getReference()+" |Quantite Demandee: "+detail.getQteVendue()+" |Prix: "+detail.getPrixVente());
        }
        System.out.println("Montant restant: "+(dette.getMontant() - dette.getMontant_verse()));
    }
    @Override
    public void showPaiements(Dette dette) {

        List<Paiement> paiements = paiementService.selectByDette(dette);
        paiementView.display(paiements);
    }
    
}
