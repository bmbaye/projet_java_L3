package com.file_rouge.datas.repository.Jdbc_impl;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;

import com.file_rouge.core.repository.impl.RepositoryJdbcImpl;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Dette;
import com.file_rouge.datas.entities.Utilisateur;
import com.file_rouge.datas.enums.StatutDette;
import com.file_rouge.datas.repository.DettesRepository;

public class DetteRepositoryImpl extends RepositoryJdbcImpl<Dette> implements DettesRepository{
    public DetteRepositoryImpl(){
        super("dette", Dette.class);
    }

    @Override
    public int insert(Dette dette) {
        //recuperer les champs de l'entite
        Field[] fields = dette.getClass().getDeclaredFields();
        for(int i=0; i<fields.length; i++) {
            System.out.println(fields[i].getName());
        }
        System.out.println(dette.toString());
        Client client = dette.getClient();
        
        //Creer un StringBuilder qui contient les colonnes
        StringBuilder columns = new StringBuilder();

        //Creer un StringBuilder qui contient les ?
        StringBuilder values = new StringBuilder();

        //Remplir les StringBuilders avec les colonnes et les?
        String query= this.generateInsertRequest(fields, columns, values);
        
        //Executer la requete
        try {
            this.initPs(query);
            System.out.println(query);
            this.ps.setDouble(1, dette.getMontant());
            this.ps.setDouble(2, dette.getMontantVerse());
            this.ps.setInt(3, client.getId());
            this.ps.setInt(4, dette.getStatut().ordinal());
            this.ps.setInt(5, dette.getCreatorUser().getId());
            this.ps.setInt(6, dette.getCreatorUser().getId());
            LocalDateTime localDate =LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(localDate);
            this.ps.setTimestamp(7, timestamp);
            this.ps.setTimestamp(8, timestamp);
            int rowsAffected =this.ps.executeUpdate();
            if(rowsAffected > 0){
                try(ResultSet rs = this.ps.getGeneratedKeys()){
                    if(rs.next()){
                        return rs.getInt(1);
                    }
                }
                return 0;
            }else{
                return 0;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Dette> getByStatut(StatutDette statut) {
         try {
            String sql = "SELECT * FROM dette WHERE Statut = ?";
            this.initPs(sql);
            this.ps.setInt(1,  statut.ordinal());
            ResultSet rs = this.ps.executeQuery();
            return this.convertToResultList(rs);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Dette> getByStatut(StatutDette statut, Client client) {
        try {
            String sql = "SELECT * FROM dette WHERE Statut = ? AND client_id = ?";
            this.initPs(sql);
            this.ps.setInt(1,  statut.ordinal());
            this.ps.setInt(2,  client.getId());
            ResultSet rs = this.ps.executeQuery();
            return this.convertToResultList(rs);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Dette> getByclient(Client client) {
        try {
            String sql = "SELECT * FROM dette WHERE client_id = ?";
            this.initPs(sql);
            this.ps.setInt(1,  client.getId());
            ResultSet rs = this.ps.executeQuery();
            return this.convertToResultList(rs);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Dette findById(int id) {
        try {
            String sql = "SELECT * FROM dette WHERE id = ?";
            this.initPs(sql);
            this.ps.setInt(1,  id);
            ResultSet rs = this.ps.executeQuery();
            return this.convertToObject(rs);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int update(Dette dette) {
        Field[] field = this.getClassFields(dette).toArray(new Field[0]);
        String query = this.generateUpdateRequete(field);
        System.out.println(query);
        try {
            this.initPs(query);
            this.ps.setDouble(1, dette.getMontant());
            this.ps.setDouble(2, dette.getMontantVerse());
            this.ps.setInt(3, dette.getClient().getId());
            this.ps.setInt(4, dette.getStatut().ordinal());
            LocalDateTime localDate =LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(localDate);
            this.ps.setTimestamp(5, timestamp); 
            this.ps.setTimestamp(6, timestamp);
            this.ps.setInt(7, dette.getCreatorUser().getId());
            this.ps.setInt(8, dette.getUpdateUser().getId());
            this.ps.setInt(9, dette.getId());
            return this.ps.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Dette findById(int id, List<Dette> dettes) {
        for(Dette dette : dettes){
            if(dette.getId() == id){
                return dette;
            }
        }
        return null;
    }

    @Override
    public String generateInsertRequest(Field[] fields, StringBuilder columns, StringBuilder values) {
        for (int i = 0; i < fields.length; i++) {
            if(fields[i].getName().compareTo("paiements") !=0 && fields[i].getName().compareTo("details") !=0  && fields[i].getName().compareTo("montantRestant" ) !=0 && fields[i].getName().compareTo("id" ) !=0){
                if(i ==0){
                    if(fields[i].getName().compareTo("client") ==0){
                        columns.append("client_id");
                    }else{
                        columns.append(fields[i].getName());
                        values.append("?");
                    }
                }else{
                    if(fields[i].getName().compareTo("montantVerse") ==0){
                        columns.append(", ").append("montant_verse");
                        values.append(", ?");
                    }else if (fields[i].getName().compareTo("client") ==0) {
                        columns.append(", ").append("client_id");
                        values.append(", ?");
                    }else{
                        columns.append(", ").append(fields[i].getName());
                        values.append(", ?");
                    }
                }
            }
            
        }
        columns.append(", creator_user_id, update_user_id, created_at, updated_at");
        values.append(", ?, ?, ?, ?");

        //Creer la requete SQL d'insertion
        String query = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ")";
        System.out.println( query);
        return query;
    }
    
}
