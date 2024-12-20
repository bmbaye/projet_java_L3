package com.file_rouge.datas.repository;

import java.lang.reflect.Field;

import com.file_rouge.core.repository.Repository;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.Detail;

public interface DetailRepository extends Repository<Detail>{
    Detail findByArticle(Article article);
    String generateInsertRequest(Field[] fields, StringBuilder columns, StringBuilder values);
}
