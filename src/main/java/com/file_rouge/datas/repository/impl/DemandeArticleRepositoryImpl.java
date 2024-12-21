package com.file_rouge.datas.repository.impl;

import java.util.List;

import com.file_rouge.core.repository.impl.RepositoryListImpl;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Demande;
import com.file_rouge.datas.entities.DemandeArticle;
import com.file_rouge.datas.repository.DemandeArticleRepository;

public class DemandeArticleRepositoryImpl extends RepositoryListImpl<DemandeArticle> implements DemandeArticleRepository{

    @Override
    public DemandeArticle findById(int id) {
        return list.stream().filter(d ->d.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<DemandeArticle> findByDemande(Demande demande) {
       return list.stream().filter(d -> d.getDemande_id() == demande.getId()).toList();
    }
    
}
