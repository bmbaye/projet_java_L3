package com.file_rouge.service;

import java.util.List;

import com.file_rouge.core.Service.Service;
import com.file_rouge.datas.entities.Demande;
import com.file_rouge.datas.entities.DemandeArticle;

public interface DemandeArticleService extends Service<DemandeArticle> {
    List<DemandeArticle> selectByDemande(Demande demande);
}
