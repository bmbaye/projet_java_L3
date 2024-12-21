package com.file_rouge.datas.repository;


import com.file_rouge.core.repository.Repository;
import com.file_rouge.datas.entities.Role;

public interface RoleRepository extends Repository<Role>{
    Role findByName(String name);
    Role findById(int id);
}
