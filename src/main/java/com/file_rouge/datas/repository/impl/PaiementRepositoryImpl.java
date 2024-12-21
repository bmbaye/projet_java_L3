package com.file_rouge.datas.repository.impl;

import java.util.List;

import com.file_rouge.core.repository.impl.RepositoryListImpl;
import com.file_rouge.datas.entities.Dette;
import com.file_rouge.datas.entities.Paiement;
import com.file_rouge.datas.repository.PaiementRepository;

public class PaiementRepositoryImpl extends RepositoryListImpl<Paiement> implements PaiementRepository{

    @Override
    public List<Paiement> getByDette(Dette dette) {
        return list.stream().filter(p ->p.getDette_id() ==  dette.getId()).toList();
    }

    @Override
    public Paiement findById(int id) {
        return list.stream().filter(p ->p.getId() ==  id).findFirst().orElse(null);
    }
    
}
