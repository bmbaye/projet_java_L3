package com.file_rouge.datas.repository.Jpa_impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.file_rouge.core.repository.impl.RespositoryJpaImpl;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Dette;
import com.file_rouge.datas.enums.StatutDette;
import com.file_rouge.datas.repository.DettesRepository;

import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class DetteRepositoryImpl extends RespositoryJpaImpl<Dette> implements DettesRepository {

    public DetteRepositoryImpl(){
        super("Dette", Dette.class);
    }
    @Override
    public List<Dette> getByStatut(StatutDette statut) {
       String[] where = new String[] {"statut", "=", "statut"};
       try {
        return this.em.createQuery(this.generateJpql(where, this.tableName), this.type)
        .setParameter("statut", statut)
        .getResultList();
       } catch (NoResultException e) {
        return null;
       }
    }
    @Override
    public List<Dette> getByclient(Client client) {
        String[] where = new String[] {"client", "LIKE", "client"};
       try {
        return this.em.createQuery(this.generateJpql(where, this.tableName), this.type)
        .setParameter("client", client)
        .getResultList();
       } catch (NoResultException e) {
        return null;
       }
    }
    @Override
    public Dette findById(int id) {
        try {
            return this.em.find(Dette.class, id);
        } catch (NoResultException e) {
           return null;
     
        }
    }

    @Override
    public Dette findById(int id, List<Dette> dettes) {
        return dettes.stream().filter(dette ->dette.getId() ==id).findFirst().orElse(null);
    }
    @Override
    public List<Dette> getByStatut(StatutDette statut, Client client) {
        Map<String, Object> colonnes = new HashMap<>();
        colonnes.put("client", client);
        colonnes.put("statut", statut);
        StringBuilder jpql = this.generatejpql(colonnes);
        try {
            TypedQuery<Dette> query = this.em.createQuery(jpql.toString(), this.type);
            for (Map.Entry<String, Object> entry : colonnes.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }

            return query.getResultList();
        } catch (NoResultException e) {
            e.getStackTrace();
            return null;
        }
    }
    @Override
    public int update(Dette dette) {
        Map<String, Object> colones = this.generateMap(Dette.class.getDeclaredFields(), dette);
        StringBuilder jpql = this.generateUpdateRequete(dette.getId(), colones);
        EntityTransaction transaction = this.em.getTransaction();
        try {
            transaction.begin();
            Query query = this.em.createQuery(jpql.toString());
            for(Map.Entry<String, Object> entry: colones.entrySet()){
                query.setParameter(entry.getKey(), entry.getValue());
            }
            query.setParameter("id", dette.getId());
            query.executeUpdate();
            transaction.commit();

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return 0;
        }
    }
    @Override
    public String generateInsertRequest(Field[] fields, StringBuilder columns, StringBuilder values) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateInsertRequest'");
    }

}
