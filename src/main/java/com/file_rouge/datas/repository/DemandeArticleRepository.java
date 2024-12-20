package com.file_rouge.datas.repository;

import java.lang.reflect.Field;

import com.file_rouge.core.repository.Repository;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.DemandeArticle;

public interface DemandeArticleRepository extends Repository<DemandeArticle>{
    String generateInsertRequest(Field[] fields, StringBuilder columns, StringBuilder values);
    int insert(DemandeArticle demandeArticle);
} 
