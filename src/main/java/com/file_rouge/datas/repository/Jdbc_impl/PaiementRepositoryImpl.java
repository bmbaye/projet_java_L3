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
import com.file_rouge.datas.entities.Paiement;
import com.file_rouge.datas.entities.Utilisateur;
import com.file_rouge.datas.repository.PaiementRepository;

public class PaiementRepositoryImpl extends RepositoryJdbcImpl<Paiement> implements PaiementRepository{

    public PaiementRepositoryImpl(){
        super("paiement", Paiement.class);
    }

    @Override
    public int insert(Paiement paiement) {
        //recuperer les champs de l'entite
        Field[] fields = paiement.getClass().getDeclaredFields();
        for(int i=0; i<fields.length; i++) {
            System.out.println(fields[i].getName());
        }
        System.out.println(paiement.toString());
        Dette dette = paiement.getDette();
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
            this.ps.setDouble(1, paiement.getMontant());
            this.ps.setInt(2, paiement.getDette().getId());
            
            this.ps.setInt(3, paiement.getCreatorUser().getId());
            this.ps.setInt(4, paiement.getCreatorUser().getId());
            LocalDateTime localDate =LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(localDate);
            this.ps.setTimestamp(5, timestamp);
            this.ps.setTimestamp(6, timestamp);
            return this.ps.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Paiement> getByDette(Dette dette) {
         try {
            String sql = "SELECT * FROM paiement p WHERE p.dette_id = ?";
            this.initPs(sql);
            this.ps.setInt(1, dette.getId());
            ResultSet rs = this.ps.executeQuery();
            return this.convertToResultList(rs);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    @Override
    public String generateInsertRequest(Field[] fields, StringBuilder columns, StringBuilder values) {
        for (int i = 0; i < fields.length; i++) {
            if(i ==0){
                if(fields[i].getName().compareTo("dette") ==0){
                    columns.append("dette_id");
                }else{
                    columns.append(fields[i].getName());
                    values.append("?");
                }
            }else{
                if (fields[i].getName().compareTo("dette") ==0) {
                    columns.append(", ").append("dette_id");
                    values.append(", ?");
                }else{
                    columns.append(", ").append(fields[i].getName());
                    values.append(", ?");
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
