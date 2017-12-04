package com.example.hassanusman.things2do.di.module;

import android.content.Context;

import com.example.hassanusman.things2do.presistence.model.LocalDatabase;
import com.example.hassanusman.things2do.presistence.model.LocalTaskDataSource;
import com.example.hassanusman.things2do.ui.home.TaskViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by HassanUsman on 1/12/17.
 */
@Module
public class HomeActivityModule {

    @Provides
    public TaskViewModel provideTaskViewModel(Context context){
        LocalTaskDataSource localTaskDataSource=new LocalTaskDataSource(LocalDatabase
                .newInstance(context).taskDao());
        return new TaskViewModel(localTaskDataSource);
    }
}
