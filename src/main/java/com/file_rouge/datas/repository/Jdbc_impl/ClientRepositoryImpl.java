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
import com.file_rouge.datas.entities.Utilisateur;
import com.file_rouge.datas.repository.ClientRepository;
import com.file_rouge.datas.repository.UtilisateurRepository;

import jakarta.persistence.NoResultException;

public class ClientRepositoryImpl extends RepositoryJdbcImpl<Client> implements ClientRepository{

    private UtilisateurRepository userRepository;
    public ClientRepositoryImpl(UtilisateurRepository userRepository){
        super("client", Client.class);
        this.userRepository = userRepository;
    }

    @Override
    public int insert(Client client) {
        //recuperer les champs de l'entite
        Field[] fields = client.getClass().getDeclaredFields();
        for(int i=0; i<fields.length; i++) {
            System.out.println(fields[i].getName());
        }
        System.out.println(client.toString());
        Utilisateur user = client.getUtilisateur();
        if(user.getLogin() !=null){
            int id_user = this.userRepository.insert(user);
            System.out.println(id_user);
            user.setId(id_user);
        }
        client.setUtilisateur(user);
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
            this.ps.setString(1, client.getSurname());
            this.ps.setString(2, client.getTelephone());
            this.ps.setString(3, client.getAdresse());
            if(user.getId() !=0){
                this.ps.setInt(4, user.getId());
            }else{
                this.ps.setNull(4, Types.INTEGER);
            }
            this.ps.setInt(5, client.getCreatorUser().getId());
            this.ps.setInt(6, client.getCreatorUser().getId());
            LocalDateTime localDate =LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(localDate);
            this.ps.setTimestamp(7, timestamp);
            this.ps.setTimestamp(8, timestamp);
            return this.ps.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Client findBySurname(String surname) {
        String query = "SELECT * FROM client c WHERE c.surname LIKE '" + surname + "'";
        try {
            ResultSet resultSet = this.executeQuery(query);
            if (resultSet.next()) {
                return this.convertToObject(resultSet);
            }
            return null;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Client findBytelephone(String telephone) {
        String query = "SELECT * FROM client c WHERE c.telephone LIKE '" + telephone + "'";
        try {
            ResultSet resultSet = this.executeQuery(query);
            if (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getInt("id"));
                client.setSurname(resultSet.getString("surname"));
                client.setTelephone(resultSet.getString("telephone"));
                client.setAdresse(resultSet.getString("adresse"));
                Utilisateur user = this.userRepository.findById(resultSet.getInt("user_id"));
                client.setUtilisateur(user);

                return client;
            }
            return null;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Client> filterByCompte(int val) {
        if(val !=0 && val !=1){
            return null;
        }

        try {
            String query;
            if(val ==1){
                 query = "SELECT * FROM client c WHERE c.user_id IS NOT NULL";
            }else{
                 query = "SELECT * FROM client c WHERE c.user_id IS NULL";
            }
            try {
                ResultSet resultSet = this.executeQuery(query);
                return this.convertToResultList(resultSet);
            } catch (Exception e) {
                // TODO: handle exception
                e.getStackTrace();
                return null;
            }
        } catch (NoResultException e) {
            // TODO: handle exception
            e.getStackTrace();
            return null;
        }
    }

    @Override
    public String generateInsertRequest(Field[] fields, StringBuilder columns, StringBuilder values) {
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
        columns.append(", creator_user_id, update_user_id, created_at, updated_at");
        values.append(", ?, ?, ?, ?");

        //Creer la requete SQL d'insertion
        String query = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ")";
        System.out.println( query);
        return query;
    }

    @Override
    public Client findById(int id) {
        try {
            String sql = "SELECT * FROM client c WHERE c.id = ?";
            this.initPs(sql);
            this.ps.setInt(1, id);
            ResultSet rs = this.ps.executeQuery();
            return this.convertToObject(rs);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    @Override
    public Client findByUserId(int user_id) {
        try {
            String sql = "SELECT * FROM client c WHERE c.user_id = ?";
            this.initPs(sql);
            this.ps.setInt(1, user_id);
            ResultSet rs = this.ps.executeQuery();
            if (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt("id"));
                client.setSurname(rs.getString("surname"));
                client.setTelephone(rs.getString("telephone"));
                client.setAdresse(rs.getString("adresse"));
                Utilisateur user = this.userRepository.findById(rs.getInt("user_id"));
                client.setUtilisateur(user);

                return client;
            }
            return null;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    
    }

    @Override
    public int update(Client client) {
        Field[] fields = this.getClassFields(client).toArray(new Field[0]);
        String sql = this.generateUpdateRequete(fields);
        Utilisateur user = client.getUtilisateur();
        if(user.getLogin() !=null){
            int id_user = this.userRepository.insert(user);
            System.out.println(id_user);
            user.setId(id_user);
        }
        client.setUtilisateur(user);
        try {
            this.initPs(sql);
            System.out.println(sql);
           this.ps.setString(1, client.getSurname());
            this.ps.setString(2, client.getTelephone());
            this.ps.setString(3, client.getAdresse());
            if(user.getId() !=0){
                this.ps.setInt(4, user.getId());
            }else{
                this.ps.setNull(4, Types.INTEGER);
            }
            LocalDateTime localDate =LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(localDate);
            this.ps.setTimestamp(5, timestamp);
            this.ps.setTimestamp(6, timestamp);
            this.ps.setInt(7, client.getCreatorUser().getId());
            this.ps.setInt(8, client.getCreatorUser().getId());
            this.ps.setInt(9, client.getId());
            return this.ps.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }
    
}
