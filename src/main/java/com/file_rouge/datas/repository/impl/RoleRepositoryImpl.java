package com.file_rouge.datas.repository.impl;

import com.file_rouge.core.repository.impl.RepositoryListImpl;
import com.file_rouge.datas.entities.Role;
import com.file_rouge.datas.repository.RoleRepository;

public class RoleRepositoryImpl extends RepositoryListImpl<Role> implements RoleRepository {

    @Override
    public Role findByName(String name) {
        return list.stream().filter(r ->r.getNom().compareToIgnoreCase(name) ==0).findFirst().orElse(null);
    }

    @Override
    public Role findById(int id) {
        return list.stream().filter(r ->r.getId() ==id).findFirst().orElse(null);
    }
    
}
