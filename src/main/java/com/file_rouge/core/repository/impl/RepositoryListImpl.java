package com.file_rouge.core.repository.impl;

import java.util.ArrayList;
import java.util.List;

import com.file_rouge.core.repository.Repository;

public class RepositoryListImpl<T> implements Repository<T> {
    protected List<T> list = new ArrayList<T>();

    @Override
    public List<T> findAll() {
        return this.list;
    }

    @Override
    public void insert(T object) {
        this.list.add(object);
    }
    
}
