package com.file_rouge.core.datasBase;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;

public interface DatasBase<T> {
    void getConnexion() throws SQLException;
    void closeConenxion() throws SQLException;
    ResultSet executeQuery(String sql) throws SQLException;
    int executeUpdate(Object entity, int id, String ssql) throws SQLException;
    String generateSql();
    void setFields(Object entity);
    void initPs(String sql) throws SQLException;
    List<Field> getClassFields(Object entity);
    T convertToObject(ResultSet resultSet);
    List<T> convertToResultList(ResultSet rs);

}
