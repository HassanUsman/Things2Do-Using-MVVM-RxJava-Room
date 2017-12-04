package com.example.hassanusman.things2do.presistence.model;

import com.example.hassanusman.things2do.DataSource;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by HassanUsman on 1/12/17.
 */

public class LocalTaskDataSource implements DataSource<Task> {

    private TaskDao taskDao;
    public LocalTaskDataSource(TaskDao taskDao){
        this.taskDao=taskDao;
    }

    @Override
    public Flowable<Task> get(Task task) {
        return taskDao.getTask(task.getTaskId());
    }

    @Override
    public Flowable<List<Task>> getAll() {
        return taskDao.getAllTasks();
    }

    @Override
    public void insert(Task task) {
        taskDao.insert(task);
    }

    @Override
    public void delete(Task task) {
        taskDao.deleteTask(task.getTaskId());
    }

    @Override
    public void update(Task task) {
        taskDao.updateTask(task.getTaskId(),task.getName(),task.getPending(),task.getDescription());
    }

}
