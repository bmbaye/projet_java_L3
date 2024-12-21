package com.file_rouge.datas.repository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.file_rouge.core.repository.Repository;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Demande;
import com.file_rouge.datas.enums.EtatDemande;

public interface DemandeRepository extends Repository<Demande> {
    List<Demande> getByEtatByClient(Client client, EtatDemande etat);
    List<Demande> getByEtat(EtatDemande etat);
    List<Demande> getByClient(Client client);
    Demande findById(int id);
    int update(Demande demande);
}
