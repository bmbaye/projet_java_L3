package com.file_rouge.core.Factory.impl;

import com.file_rouge.core.Factory.IFactory;
import com.file_rouge.datas.repository.impl.PaiementRepositoryImpl;
import com.file_rouge.service.impl.PaiementServiceImpl;
import com.file_rouge.views.impl.PaiementViewImpl;

public class PaiementFactoryImpl implements IFactory{

    @Override
    public PaiementRepositoryImpl getRepositoryInstence() {
        return new PaiementRepositoryImpl();
    }

    @Override
    public PaiementServiceImpl getServiceInstence() {
        return new PaiementServiceImpl(getRepositoryInstence());
    }

    @Override
    public PaiementViewImpl getViewInstence() {
        return new PaiementViewImpl();
    }
    
}
