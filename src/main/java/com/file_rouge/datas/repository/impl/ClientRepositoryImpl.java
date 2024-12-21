package com.file_rouge.datas.repository.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.file_rouge.core.repository.impl.RepositoryListImpl;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Utilisateur;
import com.file_rouge.datas.repository.ClientRepository;
import com.file_rouge.datas.repository.UtilisateurRepository;

public class ClientRepositoryImpl extends RepositoryListImpl<Client> implements ClientRepository{

    @Override
    public Client findById(int id) {
        return this.list.stream().filter(cl -> cl.getId() ==id).findFirst().orElse(null);
    }

    @Override
    public Client findBySurname(String surname) {
        return this.list.stream().filter(cl -> cl.getSurname().compareToIgnoreCase(surname)==0).findFirst().orElse(null);
    }

    @Override
    public Client findBytelephone(String telephone) {
        return this.list.stream().filter(cl -> cl.getTelephone().compareToIgnoreCase(telephone)==0).findFirst().orElse(null);
    }

    @Override
    public List<Client> filterByCompte(int val, List<Utilisateur> users) {
        List<Client> avecCompte = new ArrayList<Client>();
        List<Client> sansCompte = new ArrayList<Client>();
        for (Client client : list) {
            Utilisateur user = users.stream().filter(u -> u.getClient_id() == client.getId()).findFirst().orElse(null);
            if(user != null){
                avecCompte.add(client);
            }else{
                sansCompte.add(client);
            }
        }
        if(val ==1){
            return avecCompte;
        }else{
            return sansCompte;
        }
    }

    @Override
    public Client findByUserId(int user_id, List<Utilisateur> users) {
        Utilisateur user = users.stream().filter(u->u.getId() == user_id).findFirst().orElse(null);
        if(user !=null){
            if(user.getClient_id() !=0){
                return list.stream().filter(cl ->cl.getId() == user.getClient_id()).findFirst().orElse(null);
            }
        }
        return null;
    }

    @Override
    public int update(Client client) {
        int position = -1;
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getId() == client.getId()){
                position =i;
                break;
            }
        }
        this.list.set(position, client);
        return 1;
    }
    
}
