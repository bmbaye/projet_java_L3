package com.file_rouge.service;

import java.util.List;
import java.util.Map;

import com.file_rouge.core.Service.Service;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Dette;
import com.file_rouge.datas.enums.StatutDette;

public interface DetteService extends Service<Dette> {
    List<Dette> selectByStatuDette(StatutDette statut);
    boolean checkSolde(Double montant, Double montantVerse);
    Dette selectById(int id, List<Dette> dettes);
    void update(Dette dette);
    List<Dette> selectByClient(Client client, StatutDette statut);
    List<Dette> selectAll();
}
