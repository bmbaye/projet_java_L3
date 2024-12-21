package com.file_rouge.datas.repository;

import java.lang.reflect.Field;
import java.util.List;

import com.file_rouge.core.repository.Repository;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.Demande;
import com.file_rouge.datas.entities.Detail;
import com.file_rouge.datas.entities.Dette;

public interface DetailRepository extends Repository<Detail>{
    Detail findByArticle(Article article);
    List<Detail> findByDette(Dette dette);
    Detail findById(int id);
}
