package com.file_rouge.datas.repository.Jpa_impl;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.file_rouge.core.repository.impl.RespositoryJpaImpl;
import com.file_rouge.datas.entities.Utilisateur;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.Role;
import com.file_rouge.datas.enums.Etat;
import com.file_rouge.datas.repository.UtilisateurRepository;

import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;




public class UtilisateurRepositoryImpl extends RespositoryJpaImpl<Utilisateur> implements UtilisateurRepository{

    public UtilisateurRepositoryImpl(){
        super("Utilisateur", Utilisateur.class);
    }

    @Override
    public Utilisateur findByLogin(String login) {
       String[] where = new String[] {"login", "LIKE", "login"};
        try {
            return this.em.createQuery(this.generateJpql(where, this.tableName), type)
            .setParameter("login", login)
            .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Utilisateur findById(int id) {
        Utilisateur compte = this.em.find(this.type, id);
        return compte !=null ? compte: null;
    }

    @Override
    public void desactiver(int id) {
            Utilisateur user = this.findById(id);
            user.setEtat(Etat.DESACTIVE);
            this.update(user);
    }

    @Override
    public List<Utilisateur> findByEtat(Etat etat) {
        String[] where = new String[] {"etat", "=", "etat"};
        try {
            return em.createQuery(generateJpql(where, tableName), type)
            .setParameter("etat", etat)
            .getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Utilisateur> findByrole(Role role) {
        String[] where = new String[] {"role", "=", "role"};
        try {
            return em.createQuery(generateJpql(where, tableName), type)
            .setParameter("role", role)
            .getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int update(Utilisateur utilisateur) {
        Map<String, Object> colones = new HashMap<>();
        colones.put("login", utilisateur.getLogin());
        colones.put("password", utilisateur.getPassword());
        colones.put("nom", utilisateur.getNom());
        colones.put("prenom", utilisateur.getPrenom());
        colones.put("etat", utilisateur.getEtat());
        StringBuilder jpql = this.generateUpdateRequete(utilisateur.getId(), colones);
        EntityTransaction transaction = this.em.getTransaction();
        try {
            transaction.begin();
            Query query = this.em.createQuery(jpql.toString());
            for(Map.Entry<String, Object> entry: colones.entrySet()){
                query.setParameter(entry.getKey(), entry.getValue());
            }
            query.setParameter("id", utilisateur.getId());
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

    @Override
    public Utilisateur selectByLoginAndPass(String login, String password) {
        String query = "SELECT u FROM Utilisateur u WHERE u.login LIKE :login AND u.password LIKE :password";
        try {
            return this.em.createQuery(query, type)
            .setParameter("login", login)
            .setParameter("password", password)
            .getSingleResult();
        } catch (NoResultException e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
       
    }
}
