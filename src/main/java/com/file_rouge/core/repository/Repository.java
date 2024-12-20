package com.file_rouge.core.repository;

import java.util.List;
import java.util.Map;
import java.lang.reflect.Field;

public interface Repository<T> {
    Object findBy(Object object);
    List<T> findAll();
    int insert(T object);
    StringBuilder generateUpdateRequete(int id, Map<String, Object> colonnes);
    String generateUpdateRequete(Field[] fields);
}
