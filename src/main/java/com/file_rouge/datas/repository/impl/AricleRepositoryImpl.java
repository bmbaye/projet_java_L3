package com.file_rouge.datas.repository.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.file_rouge.core.repository.impl.RepositoryListImpl;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.Demande;
import com.file_rouge.datas.entities.DemandeArticle;
import com.file_rouge.datas.entities.Detail;
import com.file_rouge.datas.entities.Dette;
import com.file_rouge.datas.repository.ArticleRepository;
import com.file_rouge.datas.repository.DemandeArticleRepository;
import com.file_rouge.datas.repository.DetailRepository;

public class AricleRepositoryImpl extends RepositoryListImpl<Article> implements ArticleRepository{

    private DetailRepository detailRepository;
    private DemandeArticleRepository demandeArticleRepo;
    public AricleRepositoryImpl() {
    }
    public AricleRepositoryImpl(DetailRepository detailRepository,DemandeArticleRepository demandeArticleRepo){
        this.detailRepository = detailRepository;
        this.demandeArticleRepo = demandeArticleRepo;
    }
    @Override
    public Article findById(int id) {
        return this.list.stream().filter(a ->a.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Article findBY(Article article) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findBY'");
    }

    @Override
    public List<Article> findBYDette(Dette dette) {
        List<Article> articles = new ArrayList<Article>();
        List<Detail> details = detailRepository.findByDette(dette);
        for (Detail detail : details) {
            Article article = null;
            article = this.findById(detail.getArticle_id());
            if(article == null) {
                articles.add(article);
            }
        }
        return articles;
    }

    @Override
    public List<Article> findByDemande(Demande demande) {
        List<Article> articles = new ArrayList<Article>();
        List<DemandeArticle> demandeDetails = demandeArticleRepo.findByDemande(demande);
        for (DemandeArticle detail : demandeDetails) {
            Article article = null;
            article = this.findById(detail.getArticle_id());
            if(article == null) {
                articles.add(article);
            }
        }
        return articles;
    }

    @Override
    public int updateArticle(Article article) {
        int position = -1;
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getId() == article.getId()){
                position =i;
                break;
            }
        }
        this.list.set(position, article);
        return 1;
    }
    
}
