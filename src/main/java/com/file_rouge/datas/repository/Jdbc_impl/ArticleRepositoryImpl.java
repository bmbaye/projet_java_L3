package com.file_rouge.datas.repository.Jdbc_impl;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.file_rouge.core.repository.impl.RepositoryJdbcImpl;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Demande;
import com.file_rouge.datas.entities.Dette;
import com.file_rouge.datas.entities.Utilisateur;
import com.file_rouge.datas.repository.ArticleRepository;

public class ArticleRepositoryImpl extends RepositoryJdbcImpl<Article> implements ArticleRepository{

    public ArticleRepositoryImpl(){
        super("article", Article.class);
    }

    @Override
    public int insert(Article article) {
        //recuperer les champs de l'entite
        Field[] fields = article.getClass().getDeclaredFields();
        //Creer un StringBuilder qui contient les colonnes
        StringBuilder columns = new StringBuilder();

        //Creer un StringBuilder qui contient les ?
        StringBuilder values = new StringBuilder();

        //Remplir les StringBuilders avec les colonnes et les?
        String query= this.generateInsertRequest(fields, columns, values);
        
        //Executer la requete
        try {
            this.initPs(query);
            this.ps.setString(1, article.getReference());
            this.ps.setString(2, article.getLibelle());
            this.ps.setDouble(3, article.getPrix());
            this.ps.setInt(4, article.getQteStock());
            this.ps.setInt(5, article.getCreatorUser().getId());
            this.ps.setInt(6, article.getUpdateUser().getId());
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
    public Article findBY(Article article) {
        if(article.getLibelle() !=null){
            try {
                String sql  = "SELECT * FROM article a  WHERE a.libelle LIKE '?'";
                this.initPs(sql);
                this.ps.setString(1, article.getLibelle());
                ResultSet rs = this.ps.executeQuery();
                return this.convertToObject(rs);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        if(article.getReference() !=null){
            String query = "SELECT * FROM article a WHERE a.reference LIKE '" + article.getReference() + "'";
            try {
                ResultSet resultSet = this.executeQuery(query);
                if (resultSet.next()) {
                    Article art = new Article();
                    art.setQteStock(resultSet.getInt("qteStock"));
                    art.setReference(resultSet.getString("reference"));
                    art.setLibelle(resultSet.getString("libelle"));
                    art.setId(resultSet.getInt("id"));
                    art.setPrix(resultSet.getDouble("prix"));
                    return art;
                }
                return null;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    public Article findById(int id) {
        try {
            String sql = "SELECT * FROM article a WHERE a.id = ?";
            this.initPs(sql);
            this.ps.setInt(1, id);
            ResultSet rs = this.ps.executeQuery();
            while (rs.next()) {
                Article article = new Article();
                article.setId(rs.getInt("id"));
                article.setLibelle(rs.getString("libelle"));
                article.setReference(rs.getString("reference"));
                article.setQteStock(rs.getInt("qteStock"));
                article.setPrix(rs.getDouble("prix"));
                Utilisateur creatoerUser = new Utilisateur();
                creatoerUser.setId(rs.getInt("creator_user_id"));
                Utilisateur updateUser = new Utilisateur();
                updateUser.setId(rs.getInt("update_user_id"));
                Date sqlDate = rs.getDate("updated_at");
                LocalDateTime updateDate = sqlDate.toLocalDate().atStartOfDay();
                article.setCreatorUser(creatoerUser);
                article.setUpdateUser(updateUser);
                article.setCreateAt(LocalDateTime.now());
                article.setUpdatedAt(updateDate);
                return article;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    @Override
    public List<Article> findBYDette(Dette dette) {
        List<Article> articles = new ArrayList<Article>();
        try {
            String sql = "SELECT * FROM article a INNER JOIN detail d ON a.id = d.article_id WHERE d.dette_id = ?";
            this.initPs(sql);
            this.ps.setInt(1, dette.getId());
            ResultSet rs = this.ps.executeQuery();
            while(rs.next()){
                System.out.println("On est la");
                Article article = new Article();
                article.setId(rs.getInt("id"));
                article.setQteStock(rs.getInt("qteStock"));
                article.setReference(rs.getString("reference"));
                article.setLibelle(rs.getString("libelle"));
                article.setPrix(rs.getDouble("prix"));
                articles.add(article);
            }
            return articles;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Article> findByDemande(Demande demande) {
        List<Article> articles = new ArrayList<Article>();
        try {
            String sql = "SELECT * FROM demande_article d WHERE d.demande_id = ?";
            this.initPs(sql);
            this.ps.setInt(1, demande.getId());
            ResultSet rs = this.ps.executeQuery();
            while(rs.next()){
                int articleId = rs.getInt("article_id");
                Article article = this.findById(articleId);
                if (article != null) {
                    articles.add(article);
                }
            }
            return articles;
        } catch (Exception e) {
            return null;
            // TODO: handle exception
        }
    }

    @Override
    public int updateArticle(Article article) {
        Field[] fields = this.getClassFields(article).toArray(new Field[0]);

        // Cette methode me genere la requete de UPDATE sur toutes les colonnes sauf l'id et dont le where clause est sur l'id
        String query = this.generateUpdateRequete(fields);
        System.out.println(query);
        try {
            this.initPs(query);
            this.ps.setString(1, article.getReference());
            this.ps.setString(2, article.getLibelle());
            this.ps.setDouble(3, article.getPrix());
            this.ps.setInt(4, article.getQteStock());
            LocalDateTime localDate =LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(localDate);
            this.ps.setTimestamp(5, timestamp); 
            this.ps.setTimestamp(6, timestamp);
            this.ps.setInt(7, article.getCreatorUser().getId());
            this.ps.setInt(8, article.getUpdateUser().getId());
            this.ps.setInt(9, article.getId());
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
            if(fields[i].getName().compareTo("details") !=0){
                if(i ==0){
                    columns.append(fields[i].getName());
                    values.append("?");
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
