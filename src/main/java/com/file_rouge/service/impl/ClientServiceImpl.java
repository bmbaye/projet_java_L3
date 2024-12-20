package com.file_rouge.service.impl;

import java.util.List;

import com.file_rouge.core.Service.impl.ServiceImpl;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.repository.ClientRepository;
import com.file_rouge.service.ClientService;

public class ClientServiceImpl extends ServiceImpl<Client> implements ClientService{
    private ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> findAll() {
        return this.clientRepository.findAll();
    }

    @Override
    public int  create(Client client) {
        return this.clientRepository.insert(client);
    }

    @Override
    public Client selectBySurname(String surname) {
        return this.clientRepository.findBySurname(surname);
    }

    @Override
    public Client selectByTelephone(String tel) {
        return this.clientRepository.findBytelephone(tel);
    }

    @Override
    public List<Client> filterByCompte(int val) {
        return this.clientRepository.filterByCompte(val);
    }

    @Override
    public Client selectBySurname(String surname, List<Client> list) {
        return list.stream().filter(c ->c.getSurname().compareToIgnoreCase(surname)==0).findFirst() .orElse(null);
    }

    @Override
    public Client selectByTelephone(String tel, List<Client> list) {
        return list.stream().filter(c ->c.getTelephone().compareToIgnoreCase(tel)==0).findFirst() .orElse(null);
    }

    @Override
    public Client selectByUserId(int user_id) {
       return this.clientRepository.findByUserId(user_id);
    }

    @Override
    public int modifier(Client client) {
        return this.clientRepository.update(client);
    }
    
}
