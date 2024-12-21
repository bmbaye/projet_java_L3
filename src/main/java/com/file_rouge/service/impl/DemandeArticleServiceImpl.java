package com.file_rouge.service.impl;

import java.util.List;

import com.file_rouge.core.Service.impl.ServiceImpl;
import com.file_rouge.datas.entities.Demande;
import com.file_rouge.datas.entities.DemandeArticle;
import com.file_rouge.datas.repository.DemandeArticleRepository;
import com.file_rouge.service.DemandeArticleService;

public class DemandeArticleServiceImpl extends ServiceImpl<DemandeArticle> implements DemandeArticleService{

    DemandeArticleRepository demandeArticleReposi;

    public DemandeArticleServiceImpl(DemandeArticleRepository demandeArticleReposi){
        this.demandeArticleReposi = demandeArticleReposi;
    }

    @Override
    public List<DemandeArticle> findAll() {
        return this.demandeArticleReposi.findAll();
    }

    @Override
    public void create(DemandeArticle object) {
        this.demandeArticleReposi.insert(object);
    }

    @Override
    public List<DemandeArticle> selectByDemande(Demande demande) {
        return this.demandeArticleReposi.findByDemande(demande);
    }
    
}
