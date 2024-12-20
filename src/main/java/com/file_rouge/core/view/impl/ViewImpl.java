package com.file_rouge.core.view.impl;

import java.util.Scanner;

import com.file_rouge.core.Factory.impl.ArticleFactoryImpl;
import com.file_rouge.core.Factory.impl.ClientFactoryImpl;
import com.file_rouge.core.Factory.impl.DemandeArticleFactoryImpl;
import com.file_rouge.core.Factory.impl.DemandeFactoryImpl;
import com.file_rouge.core.Factory.impl.DetailFactoryImpl;
import com.file_rouge.core.Factory.impl.DetteFactoryImpl;
import com.file_rouge.core.Factory.impl.PaiementFactoryImpl;
import com.file_rouge.core.Factory.impl.UtilisateurFactoryImpl;
import com.file_rouge.core.Factory.impl.RoleFactoryImpl;
import com.file_rouge.core.view.View;

public abstract class ViewImpl<T> implements View<T>{
    protected Scanner scan;
    protected ClientFactoryImpl clientFactory = new ClientFactoryImpl();
    protected UtilisateurFactoryImpl utilisateurFactory = new UtilisateurFactoryImpl();
    protected RoleFactoryImpl roleFactory = new RoleFactoryImpl();
    protected ArticleFactoryImpl articleFactory = new ArticleFactoryImpl();
    protected DetteFactoryImpl detteFactory = new DetteFactoryImpl();
    protected DetailFactoryImpl detailFactory = new DetailFactoryImpl();
    protected PaiementFactoryImpl paiementFactory = new PaiementFactoryImpl();
    protected DemandeFactoryImpl demandeFactory = new DemandeFactoryImpl();
    protected DemandeArticleFactoryImpl demandeArticleFactory = new DemandeArticleFactoryImpl();
}
