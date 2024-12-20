package com.file_rouge.service;

import java.util.List;

import com.file_rouge.core.Service.Service;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.Detail;

public interface DetailService extends Service<Detail>{
    Detail selectByArticle(Article article, List<Detail> list);
}
