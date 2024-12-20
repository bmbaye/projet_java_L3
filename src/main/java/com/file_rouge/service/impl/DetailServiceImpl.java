package com.file_rouge.service.impl;

import java.util.List;

import com.file_rouge.core.Service.impl.ServiceImpl;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.Detail;
import com.file_rouge.datas.repository.DetailRepository;
import com.file_rouge.service.DetailService;

public class DetailServiceImpl extends ServiceImpl<Detail> implements DetailService {

    DetailRepository detailRepository;

    public DetailServiceImpl(DetailRepository detailRepository){
        this.detailRepository = detailRepository;
    }

    @Override
    public List<Detail> findAll() {
       return this.detailRepository.findAll();
    }
    @Override
    public int create(Detail detail) {
        return this.detailRepository.insert(detail);
    }

    @Override
    public Detail selectByArticle(Article article, List<Detail> list) {
        return list.stream().filter(d -> d.getArticle().equals(article)).findFirst().orElse(null);
    }
    
}
