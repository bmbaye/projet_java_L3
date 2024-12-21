package com.file_rouge.service.impl;

import java.util.List;

import com.file_rouge.core.Service.impl.ServiceImpl;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.Detail;
import com.file_rouge.datas.entities.Dette;
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
    public void create(Detail detail) {
        this.detailRepository.insert(detail);
    }

    @Override
    public Detail selectByArticle(Article article) {
        return this.detailRepository.findByArticle(article);
    }

    @Override
    public List<Detail> selectByDette(Dette dette) {
        return this.detailRepository.findByDette(dette);
    }
    
}
