package com.file_rouge.datas.repository.Jdbc_impl;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.file_rouge.core.repository.impl.RepositoryJdbcImpl;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Role;
import com.file_rouge.datas.entities.Utilisateur;
import com.file_rouge.datas.enums.Etat;
import com.file_rouge.datas.repository.UtilisateurRepository;
import java.sql.Timestamp;

public class UtilisateurRepositoryImpl extends RepositoryJdbcImpl<Utilisateur> implements UtilisateurRepository{

    public UtilisateurRepositoryImpl(){
        super("utilisateur", Utilisateur.class);
    }

    @Override
    public int insert(Utilisateur utilisateur) {
        //recuperer les champs de l'entite
        Field[] fields = utilisateur.getClass().getDeclaredFields();

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
            this.ps.setString(1, utilisateur.getNom());
            this.ps.setString(2, utilisateur.getPrenom());
            this.ps.setString(3, utilisateur.getLogin());
            this.ps.setString(4, utilisateur.getPassword());
            Role role = utilisateur.getRole();
            this.ps.setInt(5, role.getId());
            Etat etat = utilisateur.getEtat();
            this.ps.setInt(6, etat.ordinal());
            Utilisateur user = utilisateur.getCreatorUser();
            this.ps.setInt(7, user.getId());
            this.ps.setInt(8, user.getId());
            LocalDateTime localDate =LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(localDate);
            this.ps.setTimestamp(9, timestamp);
            this.ps.setTimestamp(10, timestamp);
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
    public Utilisateur findByLogin(String login) {
        try {
            String sql = "SELECT * FROM utilisateur a WHERE u.login LIKE ?";
            this.initPs(sql);
            this.ps.setString(1, login);
            ResultSet rs = this.ps.executeQuery();
            List<Utilisateur> resultList = new ArrayList<Utilisateur>();
            while (rs.next()) {
                Utilisateur user = new Utilisateur();
                user.setId(rs.getInt("id"));
                user.setEtat(Etat.values()[rs.getInt("etat") - 1]);
                user.setLogin(rs.getString("login"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                resultList.add(user);
                return user;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    @Override
    public Utilisateur findById(int id) {
        // TODO Auto-generated method stub
        try {
            String sql = "SELECT * FROM utilisateur u WHERE u.id = ?";
            this.initPs(sql);
            this.ps.setInt(1, id);
            ResultSet rs = this.ps.executeQuery();
            if (rs.next()) {
                Utilisateur user = new Utilisateur();
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;    }

    @Override
    public void desactiver(int id) {
        Utilisateur user = this.findById(id);
        user.setEtat(Etat.DESACTIVE);
        this.update(user);
    }

    @Override
    public List<Utilisateur> findByEtat(Etat etat) {
        try {
            String sql = "SELECT * FROM utilisateur u WHERE u.etat = ?";
            this.initPs(sql);
            this.ps.setInt(1, etat.ordinal());
            ResultSet rs = this.ps.executeQuery();
            List<Utilisateur> resultList = new ArrayList<Utilisateur>();
            while (rs.next()) {
                Utilisateur user = new Utilisateur();
                user.setId(rs.getInt("id"));
                user.setEtat(Etat.values()[rs.getInt("etat")]);
                user.setLogin(rs.getString("login"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                Role role = new Role();
                role.setId(rs.getInt("role_id"));
                user.setRole(role);
                Utilisateur creatorUser = new Utilisateur();
                creatorUser.setId(rs.getInt("creator_user_id"));
                user.setCreatorUser(creatorUser);
                Utilisateur updateUser = new Utilisateur();
                creatorUser.setId(rs.getInt("update_user_id"));
                user.setUpdateUser(updateUser);
                resultList.add(user);
            }
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return null;
    }

    @Override
    public List<Utilisateur> findByrole(Role role) {
        try {
            String sql = "SELECT * FROM utilisateur u WHERE u.role_id = ?";
            this.initPs(sql);
            this.ps.setInt(1,role.getId());
            ResultSet rs = this.ps.executeQuery();
            List<Utilisateur> resultList = new ArrayList<Utilisateur>();
            while (rs.next()) {
                Utilisateur user = new Utilisateur();
                user.setId(rs.getInt("id"));
                user.setEtat(Etat.values()[rs.getInt("etat")]);
                user.setLogin(rs.getString("login"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setRole(role);
                Utilisateur creatorUser = new Utilisateur();
                creatorUser.setId(rs.getInt("creator_user_id"));
                user.setCreatorUser(creatorUser);
                Utilisateur updateUser = new Utilisateur();
                creatorUser.setId(rs.getInt("update_user_id"));
                user.setUpdateUser(updateUser);
                resultList.add(user);
            }
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Utilisateur>();
        }
    }
    @Override
    public int update(Utilisateur utilisateur) {
        String sql = "UPDATE utilisateur SET nom = ?, prenom = ?, login = ?, password = ?, role_id = ?, etat = ?, created_at = ?, updated_at = ?, creator_user_id = ?, update_user_id = ? WHERE id = ?";
        System.out.println(sql);
        try {
            this.initPs(sql);
            this.ps.setString(1, utilisateur.getNom());
            this.ps.setString(2, utilisateur.getPrenom());
            this.ps.setString(3, utilisateur.getLogin());
            this.ps.setString(4, utilisateur.getPassword());
            Role role = utilisateur.getRole();
            this.ps.setInt(5, role.getId());
            Etat etat = utilisateur.getEtat();
            this.ps.setInt(6, etat.ordinal());
            LocalDateTime localDate =LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(localDate);
            this.ps.setTimestamp(7, timestamp);
            this.ps.setTimestamp(8, timestamp);
            Utilisateur user = utilisateur.getCreatorUser();
            this.ps.setInt(9, user.getId());
            this.ps.setInt(10, user.getId());
            this.ps.setInt(11, utilisateur.getId());
            return this.ps.executeUpdate();
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }
    @Override
    public String generateInsertRequest(Field[] fields, StringBuilder columns, StringBuilder values) {
        for (int i = 0; i < fields.length; i++) {
            if(i ==0){
                if(fields[i].getName().compareTo("client") !=0){
                    columns.append(fields[i].getName());
                    values.append("?");
                }
            }else{
                if (fields[i].getName().compareTo("client") !=0) {
                    if(fields[i].getName().compareTo("role") ==0){
                        columns.append(", ").append("role_id");
                    }else{
                        columns.append(", ").append(fields[i].getName());
                    }
                    values.append(", ?");
                }
            }
        }

        columns.append(", creator_user_id, update_user_id, created_at, updated_at");
        values.append(", ?, ?, ?, ?");

        //Creer la requete SQL d'insertion
        String query = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ")";
        return query;
    }

    @Override
    public Utilisateur selectByLoginAndPass(String login, String password) {
        try {
            String sql = "SELECT * FROM utilisateur a WHERE u.login LIKE ? and u.password Like ? ";
            this.initPs(sql);
            this.ps.setString(1, login);
            this.ps.setString(2, password);
            ResultSet rs = this.ps.executeQuery();
            List<Utilisateur> resultList = new ArrayList<Utilisateur>();
            while (rs.next()) {
                Utilisateur user = new Utilisateur();
                user.setId(rs.getInt("id"));
                user.setEtat(Etat.values()[rs.getInt("etat") - 1]);
                user.setLogin(rs.getString("login"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                Role role = new Role();
                role.setId(rs.getInt("role_id"));
                resultList.add(user);
                return user;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }
    
}
