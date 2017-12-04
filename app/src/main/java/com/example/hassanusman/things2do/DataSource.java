package com.example.hassanusman.things2do;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by HassanUsman on 1/12/17.
 */

public interface DataSource<T> {

    Flowable<T> get(T t);
    Flowable<List<T>> getAll();
    void insert(T t);
    void delete(T t);
    void update(T t);
}
