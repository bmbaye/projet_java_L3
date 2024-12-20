package com.file_rouge.datas.repository.Jpa_impl;

import java.lang.reflect.Field;

import com.file_rouge.core.repository.impl.RespositoryJpaImpl;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.DemandeArticle;
import com.file_rouge.datas.repository.DemandeArticleRepository;

public class DemandeArticleRepositoryImpl extends RespositoryJpaImpl<DemandeArticle> implements DemandeArticleRepository{
    public DemandeArticleRepositoryImpl(){
        super("DemandeArticle", DemandeArticle.class);
    }

    @Override
    public String generateInsertRequest(Field[] fields, StringBuilder columns, StringBuilder values) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateInsertRequest'");
    }

    @Override
    public int insert(DemandeArticle demandeArticle) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }
    
}
