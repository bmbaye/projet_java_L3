package com.file_rouge.datas.repository.impl;

import java.lang.reflect.Field;
import java.util.List;

import com.file_rouge.core.repository.impl.RepositoryListImpl;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.Demande;
import com.file_rouge.datas.entities.Detail;
import com.file_rouge.datas.entities.Dette;
import com.file_rouge.datas.repository.DetailRepository;

public class DetailRepositoryImpl extends RepositoryListImpl<Detail> implements DetailRepository{

    @Override
    public Detail findByArticle(Article article) {
        return list.stream().filter(d -> d.getArticle_id() == article.getId()).findFirst().orElse(null);
    }

    @Override
    public List<Detail> findByDette(Dette dette) {
        return list.stream().filter(d -> d.getDette_id() == dette.getId()).toList();
    }

    @Override
    public Detail findById(int id) {
        return list.stream().filter(d -> d.getId() == id).findFirst().orElse(null);
    }
    
}
