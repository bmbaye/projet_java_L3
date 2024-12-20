package com.file_rouge.datas.repository.Jpa_impl;

import java.lang.reflect.Field;

import com.file_rouge.core.repository.impl.RespositoryJpaImpl;
import com.file_rouge.datas.entities.Role;
import com.file_rouge.datas.repository.RoleRepository;

import jakarta.persistence.NoResultException;



public class RoleRepositoryImpl extends RespositoryJpaImpl<Role> implements RoleRepository{
    
    public RoleRepositoryImpl(){
        super("Role", Role.class);
    }

    @Override
    public Role findById(int id) {
        Role role =em.find(Role.class, id);
        return role !=null ? role: null;
    }

    @Override
    public Role findByName(String name) {
         String[] where = new String[] {"nom", "=", "nom"};
        try {
            return this.em.createQuery(this.generateJpql(where, this.tableName), type)
            .setParameter("nom", name)
            .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public String generateInsertRequest(Field[] fields, StringBuilder columns, StringBuilder values) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateInsertRequest'");
    }



}
