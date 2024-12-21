package com.file_rouge.datas.repository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.file_rouge.core.repository.Repository;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Dette;
import com.file_rouge.datas.enums.StatutDette;

public interface DettesRepository extends Repository<Dette> {
    List<Dette> getByStatut(StatutDette statut);
    List<Dette> getByStatut(StatutDette statut, Client client);
    List<Dette> getByclient(Client client);
    int update(Dette dette);
    Dette findById(int id, List<Dette> dettes);
}
