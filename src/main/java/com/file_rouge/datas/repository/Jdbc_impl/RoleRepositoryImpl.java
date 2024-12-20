package com.file_rouge.datas.repository.Jdbc_impl;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.file_rouge.core.repository.impl.RepositoryJdbcImpl;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Role;
import com.file_rouge.datas.entities.Utilisateur;
import com.file_rouge.datas.repository.RoleRepository;

public class RoleRepositoryImpl extends RepositoryJdbcImpl<Role> implements RoleRepository{

    public RoleRepositoryImpl(){
        super("role", Role.class);
    }

    @Override
    public int insert(Role role) {
        //recuperer les champs de l'entite
        Field[] fields = role.getClass().getDeclaredFields();
        for(int i=0; i<fields.length; i++) {
            System.out.println(fields[i].getName());
        }
        System.out.println(role.toString());
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
            this.ps.setString(1, role.getNom());
            this.ps.setInt(2, role.getCreatorUser().getId());
            this.ps.setInt(3, role.getCreatorUser().getId());
            LocalDateTime localDate =LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(localDate);
            this.ps.setTimestamp(4, timestamp);
            this.ps.setTimestamp(5, timestamp);
            return this.ps.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }
    @Override
    public Role findById(int id) {
        try {
            String sql = "SELECT * FROM role r WHERE r.id = ?";
            this.initPs(sql);
            this.ps.setInt(1, id);
            ResultSet rs = this.ps.executeQuery();
            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getInt("id"));
                role.setNom(rs.getString("nom"));
                return role;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    @Override
    public Role findByName(String name) {
        String query = "SELECT * FROM role r WHERE r.nom LIKE '" + name + "'";
        try {
            ResultSet resultSet = this.executeQuery(query);
            if (resultSet.next()) {
                int id =resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                Role role = new Role();
                role.setId(id);
                role.setNom(nom);
                return role;
            }
            return null;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public String generateInsertRequest(Field[] fields, StringBuilder columns, StringBuilder values) {
        for (int i = 0; i < fields.length; i++) {
            if(i ==0){
                columns.append(fields[i].getName());
                values.append("?");
            }else{
                columns.append(", ").append(fields[i].getName());
                values.append(", ?");
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
    public  List<Role> findAll(){
        String query = "SELECT * FROM role";
        try {
            ResultSet resultSet = this.executeQuery(query);
            List<Role> roles = new ArrayList<>();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                Role role = new Role();
                role.setId(id);
                role.setNom(nom);
                roles.add(role);
            }
            return roles;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
