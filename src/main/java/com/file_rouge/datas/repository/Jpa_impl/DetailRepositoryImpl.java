package com.file_rouge.datas.repository.Jpa_impl;

import java.lang.reflect.Field;

import com.file_rouge.core.repository.impl.RespositoryJpaImpl;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.Detail;
import com.file_rouge.datas.repository.DetailRepository;

import jakarta.persistence.NoResultException;

public class DetailRepositoryImpl extends RespositoryJpaImpl<Detail> implements DetailRepository {

    public DetailRepositoryImpl(){
        super("Detail", Detail.class);
    }

    @Override
    public Detail findByArticle(Article article) {
        String[] where = new String[] {"article", "=", "article"};
        try {
            return this.em.createQuery(this.generateJpql(where, this.tableName), type)
            .setParameter("telephone", article)
            .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public String generateInsertRequest(Field[] fields, StringBuilder columns, StringBuilder values) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateInsertRequest'");
    }
    
}
