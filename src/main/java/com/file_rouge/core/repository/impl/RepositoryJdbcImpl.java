package com.file_rouge.core.repository.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.file_rouge.core.datasBase.impl.DatasBaseImpl;
import com.file_rouge.core.repository.Repository;

public class RepositoryJdbcImpl<T> extends DatasBaseImpl<T> implements Repository<T> {

    protected String tableName;
    protected Class<T> type;

    public RepositoryJdbcImpl(String tableName, Class<T> type){
        super(tableName, type, "root", "");
        this.tableName = tableName;
        this.type = type;
    }
    @Override
    public Object findBy(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findBy'");
    }

    @Override
    public List<T> findAll() {
        String query = "SELECT * FROM " + tableName;
        try {
            this.initPs(query);
            ResultSet rs = ps.executeQuery(query);
            return this.convertToResultList(rs);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération des données.");
        }
    }

    @Override
    public int insert(T entity) {
        //recuperer les champs de l'entite
        Field[] fields = entity.getClass().getDeclaredFields();
        System.out.println(entity.toString());

        //Creer un StringBuilder qui contient les colonnes
        StringBuilder columns = new StringBuilder();

        //Creer un StringBuilder qui contient les ?
        StringBuilder values = new StringBuilder();

        //Remplir les StringBuilders avec les colonnes et les?
        for (int i = 0; i < fields.length; i++) {
            if(fields[i].getName().compareTo("dettes") !=0 && fields[i].getName().compareTo("demandes") !=0){
                if(i ==0){
                    if(fields[i].getName().compareTo("utilisateur") ==0){
                        columns.append("user_id");
                    }else{
                        columns.append(fields[i].getName());
                        values.append("?");
                    }
                }else{
                    if (fields[i].getName().compareTo("utilisateur") ==0) {
                        columns.append(", ").append("user_id");
                        values.append(", ?");
                    }else{
                        columns.append(", ").append(fields[i].getName());
                        values.append(", ?");
                    }
                }
            }
            
        }

        //Creer la requete SQL d'insertion
        String query = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ")";
        
        //Executer la requete
        try {
            this.initPs(query);
            System.out.println(query);
            this.setFields(entity);
            return this.ps.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }
    @Override
    public StringBuilder generateUpdateRequete(int id, Map<String, Object> colonnes) {
        StringBuilder query = new StringBuilder(String.format("UPDATE %s t SET ", this.tableName));

        int i=0;
        for(String colonne : colonnes.keySet()){
            if(i >0){
                query.append(", ");
            }
            query.append("t.").append(colonne).append(" = :").append(colonne);
            i++;
        }
        query.append(" WHERE t.id = :id");

        return query;
    }
    @Override
    public String generateUpdateRequete(Field[] fields) {
        StringBuilder query = new StringBuilder("UPDATE " + tableName + " SET ");

        for (int i = 0; i < fields.length; i++) {
            
            if (fields[i].getName().compareTo("id") != 0 && fields[i].getName().compareTo("details") != 0 && fields[i].getName().compareTo("montantRestant" ) != 0 && fields[i].getName().compareTo("paiements") != 0 && fields[i].getName().compareTo("dettes") != 0 && fields[i].getName().compareTo("demandes") != 0) {
                if(fields[i].getName().compareTo("createAt") == 0){
                    query.append("created_at").append(" = ?");
                }else if(fields[i].getName().compareTo("updatedAt") == 0){
                    query.append("updated_at").append(" = ?");
                }else if(fields[i].getName().compareTo("creatorUser") == 0){
                    query.append("creator_user_id").append(" = ?");
                }else if(fields[i].getName().compareTo("updateUser") == 0){
                    query.append("update_user_id").append(" = ?");
                }else if(fields[i].getName().compareTo("montantVerse") == 0){
                    query.append("montant_verse").append(" = ?");
                }else if(fields[i].getName().compareTo("client") == 0){
                    query.append("client_id").append(" = ?");
                }else if(fields[i].getName().compareTo("role") == 0){
                    query.append("role_id").append(" = ?");
                }else if(fields[i].getName().compareTo("statut") == 0){
                    query.append("Statut").append(" = ?");
                }else if(fields[i].getName().compareTo("utilisateur") == 0){
                    query.append("user_id").append(" = ?");
                }else{
                    query.append(fields[i].getName()).append(" = ?");
                }
                if (i < fields.length - 1) {
                    query.append(", ");
                }
            }
        }
        query.append(" WHERE id = ?");
        return query.toString();
    }
}
