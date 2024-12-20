package com.file_rouge.core.Service;

import java.util.List;

public interface Service<T> {
    List<T> findAll();
    int create(T object);
}
