package com.example.hassanusman.things2do.presistence.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

/**
 * Created by HassanUsman on 1/12/17.
 */

@Entity(tableName = "tasks")
public class Task implements Comparable<Task>{

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "task_id")
    private String taskId;


    private int pending;

    private String description;


    public Task(String name, int pending,String description){
        this.name=name;
        this.description=description;
        this.pending=pending;
        this.taskId= UUID.randomUUID().toString();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task(){

    }
    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(@NonNull String taskId) {
        this.taskId = taskId;
    }


    public int getPending() {
        return pending;
    }

    public void setPending(int pending) {
        this.pending = pending;
    }

    @Override
    public int compareTo(@NonNull Task t) {
        return this.name.compareToIgnoreCase(t.getName());
    }
}
