package com.file_rouge.service.impl;

import java.util.List;

import com.file_rouge.core.Service.impl.ServiceImpl;
import com.file_rouge.datas.entities.Utilisateur;
import com.file_rouge.datas.entities.Role;
import com.file_rouge.datas.enums.Etat;
import com.file_rouge.datas.repository.UtilisateurRepository;
import com.file_rouge.service.UtilisateurService;


public class UtilisateurServiceImpl extends ServiceImpl<Utilisateur> implements UtilisateurService{
    
    private UtilisateurRepository utilisateurRepository;

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository){
        this.utilisateurRepository = utilisateurRepository;
    }
    @Override
    public List<Utilisateur> findAll() {
        return this.utilisateurRepository.findAll();
    }

    @Override
    public void create(Utilisateur object) {
        this.utilisateurRepository.insert(object);
    }

    @Override
    public Utilisateur selectByLogin(String login) {
        return this.utilisateurRepository.findByLogin(login);
    }

    @Override
    public Utilisateur selectById(int id) {
       return this.utilisateurRepository.findById(id);
    }
    @Override
    public int modifier(Utilisateur utilisateur) {
        return this.utilisateurRepository.update(utilisateur);
    }
    @Override
    public List<Utilisateur> selectbyEtat(Etat etat) {
        return this.utilisateurRepository.findByEtat(etat);
    }
    @Override
    public List<Utilisateur> selectbyRole(Role role) {
        return this.utilisateurRepository.findByrole(role);
    }
	@Override
	public Utilisateur selectById(int id, List<Utilisateur> list) {
		return list.stream().filter(user ->user.getId() == id ).findFirst().orElse(null);
	}
    @Override
    public Utilisateur connexion(String login, String password) {
        return this.utilisateurRepository.selectByLoginAndPass(login, password);
    }
    
}
