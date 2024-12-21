package com.file_rouge.service;

import java.util.List;

import com.file_rouge.core.Service.Service;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Utilisateur;

public interface ClientService extends Service<Client>{
    Client selectBySurname(String surname);
    Client selectByTelephone(String tel);
    Client selectBySurname(String surname, List<Client> list);
    Client selectByTelephone(String tel, List<Client> list);
    List<Client> filterByCompte(int val, List<Utilisateur> users);
    Client selectByUserId(int user_id, List<Utilisateur> users);
    int modifier(Client client);
    Client selectById(int id);
}
