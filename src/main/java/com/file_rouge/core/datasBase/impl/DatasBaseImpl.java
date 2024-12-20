package com.file_rouge.core.datasBase.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.file_rouge.core.datasBase.DatasBase;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Dette;
import com.file_rouge.datas.entities.Paiement;
import com.file_rouge.datas.entities.Role;
import com.file_rouge.datas.entities.Utilisateur;
import com.file_rouge.datas.enums.Etat;
import com.file_rouge.datas.enums.EtatDemande;
import com.file_rouge.datas.enums.StatutDette;

public class DatasBaseImpl<T> implements DatasBase<T> {
    protected Connection conn = null;
    protected Class<T> type = null;
    private String tableName;
    protected PreparedStatement ps = null;
    private String url;
    private String user = "root";
    private String password = "";

    public DatasBaseImpl(String tableName, Class<T> type, String user, String password) {
        this.url = "jdbc:mysql://localhost:3306/ges_dettes_java";
        this.user = user;
        this.password = password;
        this.type = type;
        this.tableName = tableName;
        try {
            this.getConnexion() ;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void getConnexion() throws SQLException {
        if (this.conn ==null || this.conn.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(this.url, this.user, this.password);
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void closeConenxion() throws SQLException{
        if(this.conn !=null && !this.conn.isClosed()){
                conn.close();
        }
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException{
        this.initPs(sql);
        return this.ps.executeQuery();
    }

    @Override
    public int executeUpdate(Object entity, int id, String query) throws SQLException{
        //recuperer les champs de l'entite
       if (query.startsWith("UPDATE ")){ 
        
         Field[] fields =this.getClassFields(entity).toArray(new Field[0]);
         
         // initialiser le preparedStatement
        //  initPs(query);
         //Appeler setFields pour mettre a jour les champs
        //  setFields(entity);
         
         // Dernier paramètre pour l'ID (la condition WHERE)
        //  ps.setInt(fields.length + 1, id);
     
         // Exécuter l'update
         return ps.executeUpdate();
       }
       return 0;
    }

    @Override
    public String generateSql() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateSql'");
    }

    @Override
    public void initPs(String sql) throws SQLException {
        if(sql.toUpperCase().trim().startsWith("INSERT")){
            this.ps = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        }else{
            this.ps = this.conn.prepareStatement(sql);
        }
        
    }

    @Override
    public List<Field> getClassFields(Object entity) throws IllegalAccessError {
        Class<?> classe = entity.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (classe !=null && classe !=Object.class) {
            Field[] declaredFields = classe.getDeclaredFields();
            for (Field field : declaredFields) {
                if(field.getName().compareTo("id") !=0){
                    fieldList.add(field);
                }
            }
            classe = classe.getSuperclass();
        }
        return fieldList;
    }

    @Override
    public void setFields(Object entity) throws IllegalArgumentException{
        Field[] fields = this.getClassFields(entity).toArray(new Field[0]);

        for(int i =0; i<fields.length; i++){
            Field field = fields[i];
            System.out.println(field.getName());
            field.setAccessible(true);
            try {
                Object value = field.get(entity);
                if(value instanceof Integer){
                    int id =  (int)value;
                    if(id ==0){
                        continue;
                    }else{
                        this.ps.setInt(i+1, (Integer) value);
                    }
                }else if(value instanceof String){
                    System.out.println(value);
                    this.ps.setString(i +1, (String) value);
                }else if(value instanceof Utilisateur){
                    Utilisateur user = (Utilisateur) value;
                    int user_id = user.getId();
                    if(user.getLogin() ==null){
                        this.ps.setNull(i +1, Types.INTEGER);
                    }else{
                        this.ps.setInt(i+1, user_id);
                    }
                }else if(value instanceof Role){
                    int role_id = ((Role) value).getId();
                    this.ps.setInt(i +1, role_id);
                }else if(value instanceof Dette){
                    int dette_id = ((Dette) value).getId();
                    this.ps.setInt(i +1, dette_id);
                }else if(value instanceof Article){
                    int article_id = ((Article) value).getId();
                    this.ps.setInt(i +1, article_id);
                }else if(value instanceof Client){
                   continue;
                }else if(value instanceof Paiement){
                    int paiement_id = ((Paiement) value).getId();
                    this.ps.setInt(i +1, paiement_id);
                }else if(value instanceof Etat){
                    int etat = ((Etat) value).ordinal();
                    System.out.println(etat);
                    this.ps.setInt(i +1, etat);
                }else if(value instanceof LocalDateTime){
                    LocalDateTime loclaDate = (LocalDateTime)value;
                    Timestamp timestamp = Timestamp.valueOf(loclaDate);
                    this.ps.setTimestamp(i, timestamp);
                }else if(value instanceof List<?>){
                    continue;
                }else{
                    continue;
                }
            }  catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public T convertToObject(ResultSet resultSet) {
        T entity = null;
        try {
            // Créer une instance de l'entité via la réflexion
            entity = this.type.getDeclaredConstructor().newInstance();
            
            // Obtenir les noms des colonnes du ResultSet
            int columnCount = resultSet.getMetaData().getColumnCount();
            
            // Parcourir chaque colonne du ResultSet
            for (int i = 1; i <= columnCount; i++) {
                String columnName = resultSet.getMetaData().getColumnName(i);  // Nom de la colonne

                if (resultSet.next()) {
                    // Valeur de la colonne
                    Object value = resultSet.getObject(i); 
                    // Utiliser la réflexion pour trouver le champ correspondant à la colonne
                    try {
                        Field field = entity.getClass().getDeclaredField(columnName);
                        field.setAccessible(true);
                        
                        // Associer la valeur de la colonne au champ correspondant dans l'entité
                        if (field.getType().isInstance(value)) {
                            field.set(entity, value);
                        }
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        // Ignore les colonnes qui n'ont pas de correspondance dans l'entité
                    }
                }                 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return entity;
    }

    @Override
    public List<T> convertToResultList(ResultSet rs) { 
        List<T> resultList = new ArrayList<>();
        try {
            int nbrColonne = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                try {
                    T entity =null;
                    entity = type.getDeclaredConstructor().newInstance();
                    for (int i = 1; i <= nbrColonne; i++) {
                        String columnName = rs.getMetaData().getColumnLabel(i);
                        Object value = rs.getObject(i);

                        try {
                            Field field = null;
                            Class<?> currentType = type;
                            if (columnName.compareToIgnoreCase("id") == 0 ) {
                                currentType = currentType.getSuperclass();
                                field = currentType.getDeclaredField("id");
                                field.setAccessible(true);
                                field.set(entity, value);
                            }else if(columnName.compareToIgnoreCase("created_at") == 0){
                                currentType = currentType.getSuperclass();
                                field = currentType.getDeclaredField("createAt");
                                field.setAccessible(true);
                                field.set(entity, value);
                            }else if(columnName.compareToIgnoreCase("updated_at") ==0){
                                currentType = currentType.getSuperclass();
                                field = currentType.getDeclaredField("updatedAt");
                                field.setAccessible(true);
                                field.set(entity, value);
                            }else if(columnName.compareToIgnoreCase("user_id") ==0){
                                currentType = currentType.getSuperclass();
                                field = type.getDeclaredField("utilisateur");
                                field.setAccessible(true);
                                Utilisateur user = new Utilisateur();
                                if( value !=null){
                                    user.setId((int) value);
                                }
                                field.set(entity, user);
                            }else if(columnName.compareToIgnoreCase("creator_user_id") ==0){
                                currentType = currentType.getSuperclass();
                                field = currentType.getDeclaredField("creatorUser");
                                field.setAccessible(true);
                                Utilisateur user = new Utilisateur();
                                user.setId((int) value);
                                field.set(entity, user);
                            }else if(columnName.compareToIgnoreCase("update_user_id") ==0){
                                currentType = currentType.getSuperclass();
                                field = currentType.getDeclaredField("updateUser");
                                field.setAccessible(true);
                                Utilisateur user = new Utilisateur();
                                user.setId((int) value);
                                field.set(entity, user);
                            }else if(columnName.compareToIgnoreCase("montant_verse") ==0){
                                currentType = currentType.getSuperclass();
                                field = type.getDeclaredField("montantVerse");
                                field.setAccessible(true);
                                field.set(entity, value);
                            }else if(columnName.compareToIgnoreCase("Statut") ==0){
                                field = type.getDeclaredField("statut");
                                field.setAccessible(true);
                                StatutDette statut = StatutDette.values()[(int)value];
                                field.set(entity, statut);
                            }else if(columnName.compareToIgnoreCase("client_id") ==0){
                                field = type.getDeclaredField("client");
                                Client cl = new Client();
                                cl.setId((int) value);
                                field.setAccessible(true);
                                field.set(entity, cl);
                            }else if(columnName.compareToIgnoreCase("etat") ==0){
                                if(entity instanceof Utilisateur){
                                    field = type.getDeclaredField("etat");
                                    field.setAccessible(true);
                                    System.out.println((int) value -1);
                                    value = Etat.values()[(int) value];
                                    field.set(entity, value);
                                }else{
                                    field = type.getDeclaredField("etat");
                                    field.setAccessible(true);
                                    value = EtatDemande.valueOf(value.toString().toUpperCase());
                                    field.set(entity, value);
                                }
                            }else{
                                field = type.getDeclaredField(columnName);
                                field.setAccessible(true);
                                field.set(entity, value);
                            }
                            
                            System.out.println(resultList);
                        } catch (NoSuchFieldException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (SecurityException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        
                    }
                    resultList.add(entity);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException("Impossible de créer une instance pour la classe : " + type.getName(), e);
                }
                
            }
            return resultList;
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
}
