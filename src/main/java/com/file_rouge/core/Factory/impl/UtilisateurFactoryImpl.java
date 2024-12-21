package com.file_rouge.core.Factory.impl;

import com.file_rouge.core.Factory.IFactory;
import com.file_rouge.datas.repository.UtilisateurRepository;
import com.file_rouge.datas.repository.impl.UtilisateurReositoryImpl;
import com.file_rouge.service.UtilisateurService;
import com.file_rouge.service.impl.UtilisateurServiceImpl;
import com.file_rouge.views.impl.UtilisateurViewImpl;

public class UtilisateurFactoryImpl implements IFactory{
    @Override
    public UtilisateurRepository getRepositoryInstence(){
        return new UtilisateurReositoryImpl();
    }

    @Override
    public UtilisateurService getServiceInstence() {
        return new UtilisateurServiceImpl(getRepositoryInstence());
    }

    @Override
    public UtilisateurViewImpl getViewInstence() {
        return new UtilisateurViewImpl();
    }
    
}
