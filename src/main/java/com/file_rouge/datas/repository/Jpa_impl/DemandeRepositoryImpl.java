package com.file_rouge.datas.repository.Jpa_impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.file_rouge.core.repository.impl.RespositoryJpaImpl;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Demande;
import com.file_rouge.datas.entities.Dette;
import com.file_rouge.datas.enums.EtatDemande;
import com.file_rouge.datas.repository.DemandeRepository;

import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class DemandeRepositoryImpl extends RespositoryJpaImpl<Demande> implements DemandeRepository {

    public DemandeRepositoryImpl(){
        super("Demande", Demande.class);
    }

    @Override
    public List<Demande> getByEtatByClient(Client client, EtatDemande etat) {
       String[] where = new String[] {"etat", "LIKE", "etat"};
       try {
        return this.em.createQuery(this.generateJpql(where, this.tableName), this.type)
        .setParameter("etat", etat)
        .getResultList();
       } catch (NoResultException e) {
        return null;
       }
    }

    @Override
    public List<Demande> getByClient(Client client) {
        String[] where = new String[] {"client", "=", "client"};
        try {
         return this.em.createQuery(this.generateJpql(where, this.tableName), this.type)
         .setParameter("client", client)
         .getResultList();
        } catch (NoResultException e) {
         return null;
        }
    }

    @Override
    public Demande findById(int id) {
         try {
            return this.em.find(Demande.class, id);
        } catch (NoResultException e) {
           return null;
        }
    }

    @Override
    public List<Demande> getByEtat(EtatDemande etat) {
       String[] where = new String[] {"etat", "LIKE", "etat"};
       try {
        return this.em.createQuery(this.generateJpql(where, this.tableName), this.type)
        .setParameter("etat", etat)
        .getResultList();
       } catch (Exception e) {
        e.printStackTrace();
        return null;
       }
    }

    @Override
    public int update(Demande demande) {
         Map<String, Object> colones = this.generateMap(Demande.class.getDeclaredFields(), demande);
        StringBuilder jpql = this.generateUpdateRequete(demande.getId(), colones);
        EntityTransaction transaction = this.em.getTransaction();
        try {
            transaction.begin();
            Query query = this.em.createQuery(jpql.toString());
            for(Map.Entry<String, Object> entry: colones.entrySet()){
                query.setParameter(entry.getKey(), entry.getValue());
            }
            query.setParameter("id", demande.getId());
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
