package com.file_rouge.views;

import com.file_rouge.core.view.View;
import com.file_rouge.datas.entities.Article;
import com.file_rouge.datas.entities.Client;
import com.file_rouge.datas.entities.Dette;
import com.file_rouge.datas.entities.Utilisateur;

public interface DetteView extends View<Dette> {
    void saisie(Client client, Utilisateur userConnected);
    int saisieQte(Article article, String msg);
    void showArticles(Dette dette);
    void showPaiements(Dette dette);
}
