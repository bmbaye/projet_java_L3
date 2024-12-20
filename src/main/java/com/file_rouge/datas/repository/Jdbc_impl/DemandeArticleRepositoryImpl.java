package com.file_rouge.datas.repository.Jdbc_impl;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.file_rouge.core.repository.impl.RepositoryJdbcImpl;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.DemandeArticle;
import com.file_rouge.datas.entities.Dette;
import com.file_rouge.datas.repository.DemandeArticleRepository;

public class DemandeArticleRepositoryImpl extends RepositoryJdbcImpl<DemandeArticle> implements DemandeArticleRepository{

    public DemandeArticleRepositoryImpl(){
        super("demande_article", DemandeArticle.class);
    }

    @Override
    public String generateInsertRequest(Field[] fields, StringBuilder columns, StringBuilder values) {
        for (int i = 0; i < fields.length; i++) {
            if(columns.toString().isEmpty()){
                if(fields[i].getName().compareTo("demande") ==0){
                    columns.append("demande_id");
                    values.append("?");
                }else if(fields[i].getName().compareTo("article") ==0){
                    columns.append("article_id");
                    values.append("?");
                }else{
                    columns.append(fields[i].getName());
                    values.append("?");
                }
            }else{
                if(fields[i].getName().compareTo("demande") ==0){
                    columns.append(", demande_id");
                    values.append(", ?");
                }else if(fields[i].getName().compareTo("article") ==0){
                    columns.append(", article_id");
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

    @Override
    public int insert(DemandeArticle demandeArticle) {
        //recuperer les champs de l'entite
        Field[] fields = demandeArticle.getClass().getDeclaredFields();
        for(int i=0; i<fields.length; i++) {
            System.out.println(fields[i].getName());
        }
       
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
            this.ps.setDouble(1, demandeArticle.getQuantite());
            this.ps.setInt(2, demandeArticle.getDemande().getId());
            this.ps.setInt(3, demandeArticle.getArticle().getId());
            this.ps.setInt(4, demandeArticle.getCreatorUser().getId());
            this.ps.setInt(5, demandeArticle.getCreatorUser().getId());
            LocalDateTime localDate =LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(localDate);
            this.ps.setTimestamp(6, timestamp);
            this.ps.setTimestamp(7, timestamp);
            return this.ps.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }
    
}
