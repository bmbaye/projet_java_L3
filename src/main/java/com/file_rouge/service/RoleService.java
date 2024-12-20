package com.file_rouge.service;


import com.file_rouge.core.Service.Service;
import com.file_rouge.datas.entities.Role;

public interface RoleService extends Service<Role>{

    Role selectById(int id);
    Role selectByName(String name);
    
}
