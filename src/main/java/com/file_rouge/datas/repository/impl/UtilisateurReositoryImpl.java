package com.file_rouge.datas.repository.impl;

import java.util.List;

import com.file_rouge.core.repository.impl.RepositoryListImpl;
import com.file_rouge.datas.entities.Role;
import com.file_rouge.datas.entities.Utilisateur;
import com.file_rouge.datas.enums.Etat;
import com.file_rouge.datas.repository.UtilisateurRepository;

public class UtilisateurReositoryImpl extends RepositoryListImpl<Utilisateur> implements UtilisateurRepository{

    @Override
    public Utilisateur findByLogin(String login) {
        return list.stream().filter(u -> u.getLogin().compareToIgnoreCase(login) == 0).findFirst().orElse(null);
    }

    @Override
    public void desactiver(int id) {
        for (Utilisateur utilisateur : list) {
            if(utilisateur.getId() ==id){
                utilisateur.setEtat(Etat.DESACTIVE);
            }
        }
    }

    @Override
    public List<Utilisateur> findByEtat(Etat etat) {
        return list.stream().filter(u -> u.getEtat() == etat).toList();
    }

    @Override
    public int update(Utilisateur utilisateur) {
        int position = -1;
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getId() == utilisateur.getId()){
                position =i;
                break;
            }
        }
        this.list.set(position, utilisateur);
        return 1;
    }

    @Override
    public List<Utilisateur> findByrole(Role role) {
        return list.stream().filter(u -> u.getRole_id() == role.getId()).toList();
    }

    @Override
    public Utilisateur selectByLoginAndPass(String login, String password) {
        return list.stream().filter(u -> u.getLogin().compareToIgnoreCase(login) == 0 && u.getPassword() == password).findFirst().orElse(null);
    }

    @Override
    public Utilisateur findById(int id) {
        return list.stream().filter(u -> u.getId() ==id).findFirst().orElse(null);
    }
    
}
