package com.file_rouge.datas.repository;

import java.lang.reflect.Field;

import com.file_rouge.core.repository.Repository;
import com.file_rouge.datas.entities.Role;

public interface RoleRepository extends Repository<Role>{
    Role findById(int id);
    Role findByName(String name);
    String generateInsertRequest(Field[] fields, StringBuilder columns, StringBuilder values);
}
