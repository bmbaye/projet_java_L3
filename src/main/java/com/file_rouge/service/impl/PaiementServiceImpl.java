package com.file_rouge.service.impl;

import java.util.List;

import com.file_rouge.core.Service.impl.ServiceImpl;
import com.file_rouge.datas.entities.Dette;
import com.file_rouge.datas.entities.Paiement;
import com.file_rouge.datas.repository.PaiementRepository;
import com.file_rouge.service.PaiementService;

public class PaiementServiceImpl extends ServiceImpl<Paiement> implements PaiementService{

    PaiementRepository paiementRepository;
    public PaiementServiceImpl(PaiementRepository paiementRepository){
        this.paiementRepository = paiementRepository;
    }

    @Override
    public List<Paiement> findAll() {
       return this.paiementRepository.findAll();
    }

    @Override
    public int  create(Paiement paiement) {
       return this.paiementRepository.insert(paiement);
    }

    @Override
    public List<Paiement> selectByDette(Dette dette) {
        return this.paiementRepository.getByDette(dette);
    }
    
}
