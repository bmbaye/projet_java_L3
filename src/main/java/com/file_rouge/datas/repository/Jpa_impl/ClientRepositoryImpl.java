package com.file_rouge.datas.repository.Jpa_impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.file_rouge.core.repository.impl.RespositoryJpaImpl;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Utilisateur;
import com.file_rouge.datas.repository.ClientRepository;
import com.file_rouge.datas.repository.UtilisateurRepository;

import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;


public class ClientRepositoryImpl extends RespositoryJpaImpl<Client> implements ClientRepository{
    private UtilisateurRepository userRepository;
    public ClientRepositoryImpl(UtilisateurRepository userRepository){
        super("Client", Client.class);
        this.userRepository = userRepository;
    }

    @Override
    public int insert(Client client) {
       try {
            em.getTransaction().begin();
            em.persist(client);
            em.getTransaction().commit();
            return 1;
       } catch (Exception e) {
            em.getTransaction().rollback();
            return 0;
       }
    }

    @Override
    public Client findBySurname(String surname) {
        String[] where = new String[] {"surname", "=", "surname"};
        try {
            return this.em.createQuery(this.generateJpql( where, this.tableName), type)
            .setParameter("surname", surname)
            .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Client findBytelephone(String telephone) {
        String[] where = new String[] {"telephone", "=", "telephone"};
        try {
            return this.em.createQuery(this.generateJpql(where, this.tableName), type)
            .setParameter("telephone", telephone)
            .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Client> filterByCompte(int val) {
       if(val !=0 && val !=1){
        return null;
       }
       try {
        return val == 1 ? this.em.createQuery(String.format("SELECT c FROM %s c WHERE c.utilisateur IS NOT NULL", this.tableName), this.type).getResultList(): this.em.createQuery(String.format("SELECT c FROM %s c WHERE c.utilisateur IS NULL", this.tableName), this.type).getResultList();
       } catch (NoResultException e) {
        return null;
       }
    }

    @Override
    public String generateInsertRequest(Field[] fields, StringBuilder columns, StringBuilder values) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateInsertRequest'");
    }

    @Override
    public Client findById(int id) {
        Client client = this.em.find(this.type, id);
        return client !=null ? client: null;
    }

    @Override
    public Client findByUserId(int user_id) {
        String query = "SELECT c FROM Client c WHERE c.utilisateur.id = :userId";
        try {
            return this.em.createQuery(query, Client.class)
                .setParameter("userId", user_id)
                .getSingleResult();
        } catch (NoResultException e) {
            // Aucun résultat trouvé, renvoyer null ou gérer l'exception
            return null;
        }
    }

    @Override
    public int update(Client client) {
         Map<String, Object> colones = new HashMap<>();
        colones.put("surname", client.getSurname());
        colones.put("telephone", client.getTelephone());
        colones.put("adresse", client.getAdresse());
        colones.put("utilisateur", client.getUtilisateur());
        StringBuilder jpql = this.generateUpdateRequete(client.getId(), colones);
        EntityTransaction transaction = this.em.getTransaction();
        try {
            transaction.begin();
            Query query = this.em.createQuery(jpql.toString());
            for(Map.Entry<String, Object> entry: colones.entrySet()){
                query.setParameter(entry.getKey(), entry.getValue());
            }
            query.setParameter("id", client.getId());
            query.executeUpdate();
            transaction.commit();

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return 0;
        }
    }
    
}
