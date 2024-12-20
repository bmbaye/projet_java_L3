package com.file_rouge.core.Factory.impl;

import com.file_rouge.core.Factory.IFactory;
import com.file_rouge.datas.repository.Jpa_impl.DetailRepositoryImpl;
import com.file_rouge.service.impl.DetailServiceImpl;

public class DetailFactoryImpl implements IFactory{

    @Override
    public DetailRepositoryImpl getRepositoryInstence() {
        return new DetailRepositoryImpl();
    }

    @Override
    public DetailServiceImpl getServiceInstence() {
        return new DetailServiceImpl(getRepositoryInstence());
    }

    @Override
    public Object getViewInstence() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getViewInstence'");
    }
    
}
