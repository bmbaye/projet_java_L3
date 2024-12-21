package com.file_rouge.core.Factory.impl;

import com.file_rouge.core.Factory.IFactory;
import com.file_rouge.datas.repository.impl.DetteRepositoryImpl;
import com.file_rouge.service.impl.DetteServiceImpl;
import com.file_rouge.views.impl.DetteViewImpl;

public class DetteFactoryImpl implements IFactory {

    @Override
    public DetteRepositoryImpl getRepositoryInstence() {
       return new DetteRepositoryImpl();
    }

    @Override
    public DetteServiceImpl getServiceInstence() {
       return new DetteServiceImpl(getRepositoryInstence());
    }

    @Override
    public DetteViewImpl getViewInstence() {
        return new DetteViewImpl();
    }
    
}
