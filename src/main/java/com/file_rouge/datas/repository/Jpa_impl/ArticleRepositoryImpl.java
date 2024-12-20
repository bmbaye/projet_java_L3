package com.file_rouge.datas.repository.Jpa_impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.file_rouge.core.repository.impl.RespositoryJpaImpl;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.Demande;
import com.file_rouge.datas.entities.DemandeArticle;
import com.file_rouge.datas.entities.Detail;
import com.file_rouge.datas.entities.Dette;
import com.file_rouge.datas.repository.ArticleRepository;

import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

public class ArticleRepositoryImpl extends RespositoryJpaImpl<Article> implements ArticleRepository{

    public ArticleRepositoryImpl(){
        super("Article", Article.class);
    }
    @Override
    public Article findBY(Article article) {
        
        if(article.getLibelle() ==null && article.getReference() ==null){
            return null;
        }
        List<Article> list = this.findAll();

        if(article.getLibelle() !=null){
            return list.stream().filter(a-> a.getLibelle().compareToIgnoreCase(article.getLibelle()) ==0).findFirst().orElse(null);
        }
        
        return list.stream().filter(a->a.getReference().compareToIgnoreCase(article.getReference()) ==0).findFirst().orElse(null);
        
    }

    @Override
    public Article findById(int id) {
        Article article = this.em.find(this.type, id);
        return article != null ? article:null;
    }
    @Override
    public List<Article> findBYDette(Dette dette) {
        List<Detail> details = dette.getDetails();
        List<Article> articles = new ArrayList<>();
        for (Detail detail : details) {
            articles.add(detail.getArticle());
        }
        return articles;
    }
    @Override
    public List<Article> findByDemande(Demande demande) {
        List<DemandeArticle> details = demande.getDemandeArticles();
        List<Article> articles = new ArrayList<>();
        for (DemandeArticle detail : details) {
            articles.add(detail.getArticle());
        }
        return articles;
    }
    @Override
    public int updateArticle(Article article) {
        Map<String, Object> colones = new HashMap<>();
        colones.put("libelle", article.getLibelle());
        colones.put("prix", article.getPrix());
        colones.put("qteStock", article.getQteStock());
        colones.put("reference", article.getReference());
        StringBuilder jpql = this.generateUpdateRequete(article.getId(), colones);
        EntityTransaction transaction = this.em.getTransaction();
        try {
            transaction.begin();
            Query query = this.em.createQuery(jpql.toString());
            for(Map.Entry<String, Object> entry: colones.entrySet()){
                query.setParameter(entry.getKey(), entry.getValue());
            }
            query.setParameter("id", article.getId());
            query.executeUpdate();
            transaction.commit();

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return 0;
        }
    }
    @Override
    public String generateInsertRequest(Field[] fields, StringBuilder columns, StringBuilder values) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateInsertRequest'");
    }
}
