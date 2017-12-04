package com.example.hassanusman.things2do.di;

import com.example.hassanusman.things2do.di.module.HomeActivityModule;
import com.example.hassanusman.things2do.ui.home.Home;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by HassanUsman on 1/12/17.
 */

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {HomeActivityModule.class})
    abstract Home bindHome();
}
