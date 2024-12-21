package com.file_rouge.core.Factory.impl;

import com.file_rouge.core.Factory.IFactory;
import com.file_rouge.datas.repository.impl.DemandeRepositoryImpl;
import com.file_rouge.service.impl.DemandeServiceImpl;
import com.file_rouge.views.impl.DemandeViewImpl;

public class DemandeFactoryImpl implements IFactory {

    @Override
    public DemandeRepositoryImpl getRepositoryInstence() {
        return new DemandeRepositoryImpl();
    }

    @Override
    public DemandeServiceImpl getServiceInstence() {
        return new DemandeServiceImpl(getRepositoryInstence());
    }

    @Override
    public DemandeViewImpl getViewInstence() {
       return new DemandeViewImpl();
    }
    
}
