package com.file_rouge.service;

import java.util.List;
import java.util.Map;

import com.file_rouge.core.Service.Service;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.Demande;
import com.file_rouge.datas.entities.Dette;

public interface ArticleService extends Service<Article>{
    Article selectBy(Article article);
    Article selectById(int id);
    Article present(Article article, List<Article> list);
    List<Article> selectByDisponibilite(int val);
    List<Article> selectByDette(Dette dette);
    List<Article> selectByDemande(Demande demande);
    void update(Article article);
}
