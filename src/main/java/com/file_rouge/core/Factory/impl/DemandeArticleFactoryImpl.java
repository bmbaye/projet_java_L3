package com.file_rouge.core.Factory.impl;

import com.file_rouge.core.Factory.IFactory;
import com.file_rouge.datas.repository.Jpa_impl.DemandeArticleRepositoryImpl;
import com.file_rouge.service.impl.DemandeArticleServiceImpl;

public class DemandeArticleFactoryImpl implements IFactory{

    @Override
    public DemandeArticleRepositoryImpl getRepositoryInstence() {
        return new DemandeArticleRepositoryImpl();
    }

    @Override
    public DemandeArticleServiceImpl getServiceInstence() {
        return new DemandeArticleServiceImpl(getRepositoryInstence());
    }

    @Override
    public Object getViewInstence() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getViewInstence'");
    }
    
}
