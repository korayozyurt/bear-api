package com.mkt.uzaktanelemanapi.service;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IBearService<T> {

    List<T> getAll();
    ResponseEntity get(String id);
    ResponseEntity<T> save(T t);
    void delete();
}
