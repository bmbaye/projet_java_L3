package com.file_rouge.datas.repository.Jpa_impl;

import java.lang.reflect.Field;
import java.util.List;

import com.file_rouge.core.repository.impl.RespositoryJpaImpl;
import com.file_rouge.datas.entities.Dette;
import com.file_rouge.datas.entities.Paiement;
import com.file_rouge.datas.repository.PaiementRepository;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class PaiementRepositoryImpl extends RespositoryJpaImpl<Paiement> implements PaiementRepository {

    public PaiementRepositoryImpl() {
        super("Paiement", Paiement.class);
        
    }

    @Override
    public List<Paiement> getByDette(Dette dette) {
        try {
            String[] where = new String[] {"dette", "=", "dette"};

            String jpql = this.generateJpql(where, this.tableName);

            TypedQuery<Paiement> query = this.em.createQuery(jpql, this.type);

            query.setParameter("dette", dette);
            return query.getResultList();
            
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public String generateInsertRequest(Field[] fields, StringBuilder columns, StringBuilder values) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateInsertRequest'");
    }
    
}
