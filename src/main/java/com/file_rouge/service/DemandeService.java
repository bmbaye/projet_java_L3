package com.file_rouge.service;

import java.util.List;

import com.file_rouge.core.Service.Service;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Demande;
import com.file_rouge.datas.enums.EtatDemande;

public interface DemandeService extends Service<Demande>{
    List<Demande> selectByEtat(Client client, EtatDemande etat);
    Demande selectById(int id);
    Demande selectById(int id, List<Demande> demandes);
    void update(Demande demande);
    List<Demande> selectByEtat(EtatDemande etat);
    
}
