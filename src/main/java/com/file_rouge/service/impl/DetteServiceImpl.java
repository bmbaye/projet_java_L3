package com.file_rouge.service.impl;

import java.util.List;
import java.util.Map;

import com.file_rouge.core.Service.impl.ServiceImpl;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Dette;
import com.file_rouge.datas.enums.StatutDette;
import com.file_rouge.datas.repository.DettesRepository;
import com.file_rouge.service.DetteService;

public class DetteServiceImpl extends ServiceImpl<Dette> implements DetteService {

    DettesRepository detteRepository;

    public DetteServiceImpl(DettesRepository detteRepository){
        this.detteRepository = detteRepository;
    }
    @Override
    public List<Dette> findAll() {
        return this.detteRepository.findAll();
    }

    @Override
    public void create(Dette dette) {
        this.detteRepository.insert(dette);
    }

    @Override
    public List<Dette> selectByStatuDette(StatutDette statut) {
        return this.detteRepository.getByStatut(statut);
    }
    @Override
    public void update(Dette dette) {
        this.detteRepository.update(dette);
    }
    @Override
    public boolean checkSolde(Double montant, Double montantVerse) {
        return Double.compare(montant, montantVerse) ==0;
    }
    @Override
    public List<Dette> selectByClient(Client client, StatutDette statut) {
        return this.detteRepository.getByStatut(statut, client);
    }
    @Override
    public Dette selectById(int id, List<Dette> dettes) {
        return this.detteRepository.findById(id, dettes);
    }
    @Override
    public List<Dette> selectAll() {
        return this.detteRepository.findAll();
    }
    
}
