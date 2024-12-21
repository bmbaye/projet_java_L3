package com.file_rouge.datas.repository.impl;

import java.util.List;

import com.file_rouge.core.repository.impl.RepositoryListImpl;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Dette;
import com.file_rouge.datas.enums.StatutDette;
import com.file_rouge.datas.repository.DettesRepository;

public class DetteRepositoryImpl extends RepositoryListImpl<Dette> implements DettesRepository{

    @Override
    public List<Dette> getByStatut(StatutDette statut) {
        return list.stream().filter(d ->d.getStatut() == statut).toList();
    }

    @Override
    public List<Dette> getByStatut(StatutDette statut, Client client) {
        return list.stream().filter(d ->d.getStatut() == statut && d.getClient_id() == client.getId()).toList();
    }

    @Override
    public List<Dette> getByclient(Client client) {
        return list.stream().filter(d ->d.getClient_id() == client.getId()).toList();
    }

    @Override
    public int update(Dette dette) {
        int position = -1;
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getId() == dette.getId()){
                position =i;
                break;
            }
        }
        this.list.set(position, dette);
        return 1;
    }

    @Override
    public Dette findById(int id, List<Dette> dettes) {
        return this.list.stream().filter(d ->d.getId() == id).findFirst().orElse(null);
    }
    
}
