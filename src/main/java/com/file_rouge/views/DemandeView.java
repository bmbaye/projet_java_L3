package com.file_rouge.views;

import com.file_rouge.core.view.View;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Demande;
import com.file_rouge.datas.entities.Utilisateur;

public interface DemandeView extends View<Demande>{
    void saisie(Client client, Utilisateur userConnected);
    void traiterDemande(Demande demande);
}
