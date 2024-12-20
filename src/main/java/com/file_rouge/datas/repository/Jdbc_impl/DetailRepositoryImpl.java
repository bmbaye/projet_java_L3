package com.file_rouge.datas.repository.Jdbc_impl;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;

import com.file_rouge.core.repository.impl.RepositoryJdbcImpl;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Detail;
import com.file_rouge.datas.entities.Dette;
import com.file_rouge.datas.entities.Utilisateur;
import com.file_rouge.datas.repository.DetailRepository;

public class DetailRepositoryImpl extends RepositoryJdbcImpl<Detail> implements DetailRepository{

    public DetailRepositoryImpl(){
        super("detail", Detail.class);
    }

    @Override
    public int insert(Detail detail) {
        //recuperer les champs de l'entite
        Field[] fields = detail.getClass().getDeclaredFields();
        for(int i=0; i<fields.length; i++) {
            System.out.println(fields[i].getName());
        }
        System.out.println(detail.toString());
        Article user = detail.getArticle();
        Dette dette = detail.getDette();
       
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
            this.ps.setDouble(1, detail.getPrixVente());
            this.ps.setInt(2, detail.getQteVendue());
            this.ps.setInt(3, detail.getDette().getId());
            this.ps.setInt(4, detail.getArticle().getId());
            this.ps.setInt(5, detail.getCreatorUser().getId());
            this.ps.setInt(6, detail.getCreatorUser().getId());
            LocalDateTime localDate =LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(localDate);
            this.ps.setTimestamp(7, timestamp);
            this.ps.setTimestamp(8, timestamp);
            int rowsAffected = this.ps.executeUpdate();
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
    public Detail findByArticle(Article article) {
        try {
            String sql = "SELECT * FROM detail WHERE article_id = ?";
            this.initPs(sql);
            this.ps.setInt(1,  article.getId());
            ResultSet rs = this.ps.executeQuery();
            return this.convertToObject(rs);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public String generateInsertRequest(Field[] fields, StringBuilder columns, StringBuilder values) {
        for (int i = 0; i < fields.length; i++) {
            if(i ==0){
                if (fields[i].getName().compareTo("prixVente") ==0) {
                    columns.append("prix_vente");
                    values.append("?");
                }else if(fields[i].getName().compareTo("qteVendue") ==0){
                    columns.append("qte_vendue");
                    values.append("?");
                }else if(fields[i].getName().compareTo("dette") ==0){
                    columns.append("dette_id");
                    values.append("?");
                }else if(fields[i].getName().compareTo("article") ==0){
                    columns.append("article_id");
                    values.append("?");
                }else{
                    columns.append(fields[i].getName());
                    values.append("?");
                }
            }else{
                if (fields[i].getName().compareTo("dette") ==0) {
                    columns.append(", ").append("dette_id");
                    values.append(", ?");
                }else if (fields[i].getName().compareTo("prixVente") ==0) {
                    columns.append(", ").append("prix_vente");
                    values.append(", ?");
                }else if (fields[i].getName().compareTo("qteVendue") ==0) {
                    columns.append(", ").append("qte_vendue");
                    values.append(", ?");
                }else if (fields[i].getName().compareTo("article") ==0) {
                    columns.append(", ").append("article_id");
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
