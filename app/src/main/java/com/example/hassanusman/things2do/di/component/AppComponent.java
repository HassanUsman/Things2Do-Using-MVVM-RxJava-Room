package com.example.hassanusman.things2do.di.component;

import android.app.Application;

import com.example.hassanusman.things2do.AppController;
import com.example.hassanusman.things2do.di.ActivityBuilder;
import com.example.hassanusman.things2do.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by HassanUsman on 1/12/17.
 */

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class,
        ActivityBuilder.class})
public interface AppComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }


    void inject(AppController appController);
}
