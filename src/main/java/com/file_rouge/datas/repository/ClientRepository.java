package com.file_rouge.datas.repository;

import java.lang.reflect.Field;
import java.util.List;

import com.file_rouge.core.repository.Repository;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Utilisateur;

public interface ClientRepository extends Repository<Client>{
    Client findBySurname(String surname);
    Client findById(int id);
    Client findBytelephone(String telephone);
    List<Client> filterByCompte(int val, List<Utilisateur> users);
    Client findByUserId(int user_id, List<Utilisateur> users);
    int update(Client client);
}
