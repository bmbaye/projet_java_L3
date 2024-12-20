package com.file_rouge.core.Factory.impl;

import com.file_rouge.core.Factory.IFactory;
import com.file_rouge.datas.repository.Jpa_impl.ClientRepositoryImpl;
import com.file_rouge.service.impl.ClientServiceImpl;
import com.file_rouge.views.impl.ClientViewImpl;

public class ClientFactoryImpl implements IFactory{

    UtilisateurFactoryImpl userFactory = new UtilisateurFactoryImpl();

    @Override
    public ClientRepositoryImpl getRepositoryInstence() {
        return new ClientRepositoryImpl(userFactory.getRepositoryInstence());
    }

    @Override
    public ClientServiceImpl getServiceInstence() {
        return new ClientServiceImpl(getRepositoryInstence());
    }

    @Override
    public ClientViewImpl getViewInstence() {
        return new ClientViewImpl();
    }
}
