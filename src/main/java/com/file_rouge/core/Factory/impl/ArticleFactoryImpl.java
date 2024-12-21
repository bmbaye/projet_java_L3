package com.file_rouge.core.Factory.impl;

import com.file_rouge.core.Factory.IFactory;
import com.file_rouge.datas.repository.impl.AricleRepositoryImpl;
import com.file_rouge.service.impl.ArticleServiceImpl;
import com.file_rouge.views.impl.ArticleViewImpl;

public class ArticleFactoryImpl implements IFactory{

    @Override
    public AricleRepositoryImpl getRepositoryInstence() {
        return new AricleRepositoryImpl();
    }

    @Override
    public ArticleServiceImpl getServiceInstence() {
        return new ArticleServiceImpl(getRepositoryInstence());
    }

    @Override
    public ArticleViewImpl getViewInstence() {
        return new ArticleViewImpl();
    }
    
}
