package com.file_rouge.views;

import com.file_rouge.core.view.View;
import com.file_rouge.datas.entities.Utilisateur;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Role;
import com.file_rouge.datas.enums.Etat;

public interface UtilisateurView extends View<Utilisateur>{

    Utilisateur saisie(Role role, Utilisateur userconnected);
    Role chooseRole();
    Etat choosEtat();
    void filtreParEtat(Etat etat);
    void filtreParRole(Role role);
    Utilisateur saisie(Client client, Role role, Utilisateur userConnected);
}
