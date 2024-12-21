package com.file_rouge.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.file_rouge.core.Service.impl.ServiceImpl;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.Demande;
import com.file_rouge.datas.entities.Dette;
import com.file_rouge.datas.repository.ArticleRepository;
import com.file_rouge.service.ArticleService;

public class ArticleServiceImpl extends ServiceImpl<Article> implements ArticleService{
    private ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Article> findAll() {
        return this.articleRepository.findAll();
    }

    @Override
    public void create(Article article) {
        this.articleRepository.insert(article);
    }

    @Override
    public Article selectBy(Article article) {
        return this.articleRepository.findBY(article);
    }

    @Override
    public Article selectById(int id) {
        return this.articleRepository.findById(id);
    }

    @Override
    public Article present(Article article, List<Article> list) {
        for (Article art : list) {
            if(art.equals(article)){
                return art;
            }
        }
        return null;
    }

    @Override
    public List<Article> selectByDette(Dette dette) {
       return this.articleRepository.findBYDette(dette);
    }

    @Override
    public List<Article> selectByDemande(Demande demande) {
        return this.articleRepository.findByDemande(demande);
    }

    @Override
    public void update(Article article) {
        this.articleRepository.updateArticle(article);
    }

	@Override
	public List<Article> selectByDisponibilite(int val) {
		List<Article> articles = this.findAll();
        List<Article> dispo = new ArrayList<Article>();
        List<Article> non_dispo = new ArrayList<Article>();
        for (Article art : articles) {
            if(art.getQteStock() >0){
                dispo.add(art);
            }else{
                non_dispo.add(art);
            }
        }
        if(val == 1){
            return dispo;
        }else if (val == 0){
            return non_dispo;
        }else{
            return new ArrayList<Article>();
        }
	}
}
