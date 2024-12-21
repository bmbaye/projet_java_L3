package com.file_rouge.datas.repository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.file_rouge.core.repository.Repository;
import com.file_rouge.datas.entities.Utilisateur;
import com.file_rouge.datas.entities.Role;
import com.file_rouge.datas.enums.Etat;

public interface UtilisateurRepository extends Repository<Utilisateur>{
    Utilisateur findByLogin(String login);
    void desactiver(int id);
    List<Utilisateur> findByEtat(Etat etat);
    int update(Utilisateur utilisateur);
    List<Utilisateur> findByrole(Role role);
    Utilisateur selectByLoginAndPass(String login, String password);
    Utilisateur findById(int id);   
}
