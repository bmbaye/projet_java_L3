package com.file_rouge.core.Factory.impl;

import com.file_rouge.core.Factory.IFactory;
import com.file_rouge.datas.repository.UtilisateurRepository;
import com.file_rouge.datas.repository.Jpa_impl.UtilisateurRepositoryImpl;
import com.file_rouge.service.UtilisateurService;
import com.file_rouge.service.impl.UtilisateurServiceImpl;
import com.file_rouge.views.impl.UtilisateurViewImpl;

public class UtilisateurFactoryImpl implements IFactory{
    @Override
    public UtilisateurRepository getRepositoryInstence(){
        return new UtilisateurRepositoryImpl();
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
