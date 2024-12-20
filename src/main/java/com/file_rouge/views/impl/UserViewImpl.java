package com.file_rouge.views.impl;

import java.util.Scanner;

import com.file_rouge.core.Factory.impl.ArticleFactoryImpl;
import com.file_rouge.core.Factory.impl.ClientFactoryImpl;
import com.file_rouge.core.Factory.impl.DemandeFactoryImpl;
import com.file_rouge.core.Factory.impl.DetteFactoryImpl;
import com.file_rouge.core.Factory.impl.PaiementFactoryImpl;
import com.file_rouge.core.Factory.impl.RoleFactoryImpl;
import com.file_rouge.core.Factory.impl.UtilisateurFactoryImpl;
import com.file_rouge.datas.entities.Utilisateur;
import com.file_rouge.views.UserView;

public abstract class UserViewImpl implements UserView {
    protected  Utilisateur userConnected;
    protected Scanner scan = new Scanner(System.in);
    protected ClientFactoryImpl clientFactory = new ClientFactoryImpl();
    protected DetteFactoryImpl detteFactory = new DetteFactoryImpl();
    protected PaiementFactoryImpl paiementFactory = new PaiementFactoryImpl();
    protected DemandeFactoryImpl demandeFactory = new DemandeFactoryImpl();
    protected ArticleFactoryImpl articleFactory = new ArticleFactoryImpl();
    protected UtilisateurFactoryImpl userFactory = new UtilisateurFactoryImpl();
    protected RoleFactoryImpl roleFactory = new RoleFactoryImpl();
}
