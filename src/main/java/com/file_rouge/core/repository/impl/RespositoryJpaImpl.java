package com.file_rouge.core.repository.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.file_rouge.core.repository.Repository;

import jakarta.annotation.PreDestroy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class RespositoryJpaImpl<T> implements Repository<T> {
    protected String tableName;
    protected EntityManager em;
    protected Class<T> type;
    private EntityManagerFactory emf =  Persistence.createEntityManagerFactory("DETTES-SQL");

    public RespositoryJpaImpl(String tableName, Class<T> type){
        this.tableName = tableName;
        this.type = type;
        if(this.em == null){
            this.em =emf.createEntityManager();
        }
    }
    @Override
    public List<T> findAll() {
        List<T> results = new ArrayList<>();
        try {
            return this.em.createQuery(String.format("SELECT t FROM %s t", tableName), type)
            .getResultList();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
        return results;
    }

    @Override
    public int insert(T object) {
       try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
            return 1;
       } catch (Exception e) {
            em.getTransaction().rollback();
            return 0;
       }
    }

    public String generateJpql(String[] where, String tableName){

        if(where.length !=3 || where.length ==0){
            throw new IllegalArgumentException("cette methode prend un tableau de 3 String en parametre");
        }else{
            return String.format("SELECT r FROM %s r WHERE r." + where[0] +" " + where[1] + " :" +where[2], tableName);
        }
    }

    public StringBuilder generatejpql(Map<String, Object> colonnes){
        StringBuilder jpql = new StringBuilder(String.format("SELECT t FROM %s t WHERE ", this.tableName));
        int i=0;
        for(String colonne: colonnes.keySet()){
            if(i >0){
                jpql.append(" AND ");
            }
            jpql.append("t.").append(colonne).append(" = :").append(colonne);
            i++;
        }

        return jpql;
    }

    @Override
    public StringBuilder generateUpdateRequete(int id, Map<String, Object> colonnes){
        StringBuilder jpql = new StringBuilder(String.format("UPDATE %s t SET ", this.tableName));

        int i=0;
        for(String colonne : colonnes.keySet()){
            if (colonne.compareTo("id") !=0) {
                if(i >0){
                    jpql.append(", ");
                }
                jpql.append("t.").append(colonne).append(" = :").append(colonne);
                i++;
            }
        }
        jpql.append(" WHERE t.id = :id");

        return jpql;
    }

    @PreDestroy // ou dans votre méthode de fermeture
    public void cleanup() {
        // Fermer EntityManager si utilisé
        if (this.em != null) {
            this.em.close();
        }

        // Fermer SessionFactory si utilis
    }

    @Override
    public Object findBy(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findBy'");
    }
    @Override
    public String generateUpdateRequete(Field[] fields) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateUpdateRequete'");
    }

    public Map<String, Object> generateMap(Field[] fields, Object entity) {
        Map<String, Object> map = new HashMap<>();
        try {
            for (Field field : fields) {
                if (field.getName().compareTo("id")!=0) {
                    field.setAccessible(true);
                    map.put(field.getName(), field.get(entity));
                }
            }
            return map;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
}
