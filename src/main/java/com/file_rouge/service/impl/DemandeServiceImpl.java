package com.file_rouge.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.file_rouge.core.Service.impl.ServiceImpl;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Demande;
import com.file_rouge.datas.enums.EtatDemande;
import com.file_rouge.datas.repository.DemandeRepository;
import com.file_rouge.service.DemandeService;;

public class DemandeServiceImpl extends ServiceImpl<Demande> implements DemandeService{

    DemandeRepository demandeRepository;

    public DemandeServiceImpl(DemandeRepository demandeRepository){
        this.demandeRepository = demandeRepository;
    }

    @Override
    public List<Demande> findAll() {
       return this.demandeRepository.findAll();
    }

    @Override
    public void create(Demande demande) {
       this.demandeRepository.insert(demande);
    }

    @Override
    public List<Demande> selectByEtat(Client client, EtatDemande etat) {
        return this.demandeRepository.getByEtatByClient(client, etat);
    }

    @Override
    public Demande selectById(int id) {
       return this.demandeRepository.findById(id);
    }

    @Override
    public void update(Demande demande) {
       this.demandeRepository.update(demande);
    }

   @Override
   public List<Demande> selectByEtat(EtatDemande etat) {
      return this.demandeRepository.getByEtat(etat);
   }

   @Override
   public Demande selectById(int id, List<Demande> demandes) {
      return demandes.stream().filter(d-> d.getId() ==id).findFirst().orElse(null);
   }
    
}
