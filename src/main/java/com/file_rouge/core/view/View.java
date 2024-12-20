package com.file_rouge.core.view;

import java.util.List;

import com.file_rouge.datas.entities.Utilisateur;

public interface View<T> {
    T saisie(Utilisateur userConnected);
    void display(List<T> list);
    void display();
}
