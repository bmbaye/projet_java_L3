package com.file_rouge.core.Factory.impl;

import com.file_rouge.core.Factory.IFactory;
import com.file_rouge.datas.repository.RoleRepository;
import com.file_rouge.datas.repository.impl.RoleRepositoryImpl;
import com.file_rouge.service.impl.RoleServiceImpl;

public class RoleFactoryImpl implements IFactory{

    public RoleRepository getRepositoryInstence() {
        return new RoleRepositoryImpl();
    }

    @Override
    public RoleServiceImpl getServiceInstence() {
        return new RoleServiceImpl(getRepositoryInstence());
    }

    @Override
    public Object getViewInstence() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getViewInstence'");
    }
    
}
