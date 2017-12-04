package com.example.hassanusman.things2do.presistence.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by HassanUsman on 1/12/17.
 */

@Dao
public interface TaskDao {

    @Query("SELECT * FROM tasks")
    Flowable<List<Task>> getAllTasks();

    @Query("SELECT * FROM tasks WHERE task_id=:id")
    Flowable<Task> getTask(String id);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Task task);

    @Query("DELETE FROM tasks WHERE task_id=:id")
    void deleteTask(String id);

    @Query("UPDATE tasks SET name=:name, pending=:pending, description=:des " +
            "WHERE task_id=:id")
    void updateTask(String id,String name,int pending,String des);

}
