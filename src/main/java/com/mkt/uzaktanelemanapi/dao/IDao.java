package com.mkt.uzaktanelemanapi.dao;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IDao<T> {
    ResponseEntity get(String id);
    List<T> getAll();
    ResponseEntity<T> save(T t);
    void delete(T t);
}
