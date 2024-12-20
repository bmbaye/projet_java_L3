package com.file_rouge.views;

import java.util.List;

import com.file_rouge.core.view.View;
import com.file_rouge.datas.entities.Dette;
import com.file_rouge.datas.entities.Paiement;
import com.file_rouge.datas.entities.Utilisateur;

public interface PaiementView extends View<Paiement>{
    void save(Utilisateur userConnected, Dette dette);
}
