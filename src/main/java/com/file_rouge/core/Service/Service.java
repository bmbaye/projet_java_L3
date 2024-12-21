package com.file_rouge.core.Service;

import java.util.List;

public interface Service<T> {
    List<T> findAll();
    void create(T object);
}
