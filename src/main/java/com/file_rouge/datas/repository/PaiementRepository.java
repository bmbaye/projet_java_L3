package com.file_rouge.datas.repository;

import java.lang.reflect.Field;
import java.util.List;

import com.file_rouge.core.repository.Repository;
import com.file_rouge.datas.entities.Dette;
import com.file_rouge.datas.entities.Paiement;

public interface PaiementRepository extends Repository<Paiement> {
    List<Paiement> getByDette(Dette dette);
    Paiement findById(int id);
}
