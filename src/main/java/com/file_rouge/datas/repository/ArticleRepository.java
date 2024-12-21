package com.file_rouge.datas.repository;


import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.file_rouge.core.repository.Repository;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.Demande;
import com.file_rouge.datas.entities.Dette;

public interface ArticleRepository extends Repository<Article>{
    Article findBY(Article article);
    Article findById(int id);
    List<Article> findBYDette(Dette dette);
    List<Article> findByDemande(Demande demande);
    int updateArticle(Article article);
}
