package com.file_rouge.views.impl;

import java.util.List;
import java.util.Scanner;

import com.file_rouge.core.view.impl.ViewImpl;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.Utilisateur;
import com.file_rouge.service.ArticleService;
import com.file_rouge.views.ArticleView;

public class ArticleViewImpl extends ViewImpl<Article> implements ArticleView{

    ArticleService articleService;

    public ArticleViewImpl(){
        if(this.scan == null){
            this.scan = new Scanner(System.in);
        }
        this.articleService = articleFactory.getServiceInstence();
    }

    @Override
    public Article saisie(Utilisateur userConnected) {
        Article article = new Article();

        String libelle;
        String ref;
        Article art = new Article();
        do {
            System.out.println("Le libelle de l'article:");
            libelle = scan.nextLine();
            art.setLibelle(libelle);
        } while (this.articleService.selectBy(art) !=null);

        do {
            System.out.println("La reference de l'article:");
            ref = scan.nextLine();
            art.setReference(ref);
        } while (this.articleService.selectBy(art) !=null);

        Double prix;
        do {
            System.out.println("Le prix de l'article:");
            prix = scan.nextDouble();
        } while (prix <=0);

        int qteSTock;
        do {
            System.out.println("La quantite en stock:");
            qteSTock = scan.nextInt();
        } while (qteSTock <=0);

        article.setReference(ref);
        article.setLibelle(libelle);
        article.setPrix(prix);
        article.setQteStock(qteSTock);

        return article;
    }

    @Override
    public void display(List<Article> list) {
        for (Article article : list) {
            System.out.println("ID "+ article.getId()+ " |Libelle "+ article.getLibelle() + " |Reference " + article.getReference() + " |Prix " + article.getPrix());
        }
    }

    @Override
    public void display() {
        List<Article> articles = this.articleService.findAll();
        this.display(articles);

    }
    
}
