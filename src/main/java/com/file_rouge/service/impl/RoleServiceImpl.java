package com.file_rouge.service.impl;

import java.util.List;

import com.file_rouge.core.Service.impl.ServiceImpl;
import com.file_rouge.datas.entities.Role;
import com.file_rouge.datas.repository.RoleRepository;
import com.file_rouge.service.RoleService;

public class RoleServiceImpl extends ServiceImpl<Role> implements RoleService{

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Override
    public int create(Role role){
       return this.roleRepository.insert(role);
    }

    @Override
    public List<Role> findAll(){
        return this.roleRepository.findAll();
    }

    @Override
    public Role selectById(int id) {
        return this.roleRepository.findById(id);
    }

    @Override
    public Role selectByName(String name) {
        return this.roleRepository.findByName(name);
    }
}
