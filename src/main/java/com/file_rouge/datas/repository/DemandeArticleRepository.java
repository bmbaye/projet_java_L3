package com.file_rouge.datas.repository;
import java.util.List;

import com.file_rouge.core.repository.Repository;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Demande;
import com.file_rouge.datas.entities.DemandeArticle;

public interface DemandeArticleRepository extends Repository<DemandeArticle>{
    DemandeArticle findById(int id);
    List<DemandeArticle> findByDemande(Demande demande);
} 
