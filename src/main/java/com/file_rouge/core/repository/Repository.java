package com.file_rouge.core.repository;

import java.util.List;
import java.util.Map;
import java.lang.reflect.Field;

public interface Repository<T> {
    List<T> findAll();
    void insert(T object);
}
