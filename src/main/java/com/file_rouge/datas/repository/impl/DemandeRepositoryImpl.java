package com.file_rouge.datas.repository.impl;

import java.util.List;

import com.file_rouge.core.repository.impl.RepositoryListImpl;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Demande;
import com.file_rouge.datas.enums.EtatDemande;
import com.file_rouge.datas.repository.DemandeRepository;

public class DemandeRepositoryImpl extends RepositoryListImpl<Demande> implements DemandeRepository {

    @Override
    public List<Demande> getByEtatByClient(Client client, EtatDemande etat) {
        return list.stream().filter(d -> d.getClient_id() == client.getId() && d.getEtat() == etat).toList();
    }

    @Override
    public List<Demande> getByEtat(EtatDemande etat) {
        return list.stream().filter(d ->  d.getEtat() == etat).toList();
    }

    @Override
    public List<Demande> getByClient(Client client) {
        return list.stream().filter(d -> d.getClient_id() == client.getId()).toList();
    }

    @Override
    public Demande findById(int id) {
        return list.stream().filter(d -> d.getId() == id).findFirst().orElse(null);
    }

    @Override
    public int update(Demande demande) {
        // TODO Auto-generated method stub
        int position = -1;
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getId() == demande.getId()){
                position =i;
                break;
            }
        }
        this.list.set(position, demande);
        return 1;
    }
    
}
