package com.file_rouge.datas.repository.Jdbc_impl;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.file_rouge.core.repository.impl.RepositoryJdbcImpl;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Demande;
import com.file_rouge.datas.enums.EtatDemande;
import com.file_rouge.datas.repository.DemandeRepository;

public class DemandeRepositoryImpl extends RepositoryJdbcImpl<Demande> implements DemandeRepository{

    public DemandeRepositoryImpl(){
        super("demande", Demande.class);
    }

    @Override
    public int insert(Demande demande) {
        //recuperer les champs de l'entite
        Field[] fields = demande.getClass().getDeclaredFields();
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
            this.ps.setDouble(1, demande.getMontant());
            Client client = demande.getClient();
            if(client.getId( ) != 0){
                this.ps.setInt(2, client.getId());
            }
            this.ps.setString(3, demande.getEtat().name()); 
            this.ps.setInt(4, demande.getCreatorUser().getId());
            this.ps.setInt(5, demande.getCreatorUser().getId());
            LocalDateTime localDate =LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(localDate);
            this.ps.setTimestamp(6, timestamp);
            this.ps.setTimestamp(7, timestamp);
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
    public List<Demande> getByEtatByClient(Client client, EtatDemande etat) {
        try {
            String sql = "SELECT * FROM demande WHERE client_id = ? AND etat LIKE ?";
            this.initPs(sql);
            this.ps.setInt(1, client.getId());
            this.ps.setString(2,  etat.name());
            ResultSet rs = this.ps.executeQuery();
            return this.convertToResultList(rs);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Demande> getByEtat(EtatDemande etat) {
        try {
            String sql = "SELECT * FROM demande WHERE etat LIKE ?";
            this.initPs(sql);
            this.ps.setString(1,  etat.name());
            ResultSet rs = this.ps.executeQuery();
            return this.convertToResultList(rs);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Demande> getByClient(Client client) {
        try {
            String sql = "SELECT * FROM demande WHERE client_id = ?";
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
    public Demande findById(int id) {
        try {
            String sql = "SELECT * FROM demande WHERE id = ?";
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
    public int update(Demande demande) {
        Field[] fields = this.getClassFields(demande).toArray(new Field[0]);
        String sql = this.generateUpdateRequete(fields);
        try {
            return this.executeUpdate(demande, demande.getId(), sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
        
    }

    @Override
    public String generateInsertRequest(Field[] fields, StringBuilder columns, StringBuilder values) {
            for (int i = 0; i < fields.length; i++) {
                if(fields[i].getName().compareTo("demandeArticles") !=0){
                    if(columns.toString().isEmpty()){
                        if(fields[i].getName().compareTo("client") ==0){
                            columns.append("client_id");
                            values.append("?");
                        }else{
                            columns.append(fields[i].getName());
                            values.append("?");
                        }
                    }else{
                        if (fields[i].getName().compareTo("client") ==0) {
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
