package com.cpunisher.pedo.dao;

import java.util.List;

public interface IOperator<T> {

    int insert(String sql, Object...args);

    int delete(String sql, Object...args);

    int update(String sql, Object...args);

    List<T> select(String sql, Object...args);
}
