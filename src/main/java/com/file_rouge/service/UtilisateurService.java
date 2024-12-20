package com.file_rouge.service;

import java.util.List;

import com.file_rouge.core.Service.Service;
import com.file_rouge.datas.entities.Utilisateur;
import com.file_rouge.datas.entities.Role;
import com.file_rouge.datas.enums.Etat;

public interface UtilisateurService extends Service<Utilisateur>{

    Utilisateur selectByLogin(String login);
    Utilisateur selectById(int id);
    Utilisateur selectById(int id, List<Utilisateur> list);
    int modifier(Utilisateur utilisateur);
    List<Utilisateur> selectbyEtat(Etat etat);
    List<Utilisateur> selectbyRole(Role role);
    Utilisateur connexion(String login, String password);
}
