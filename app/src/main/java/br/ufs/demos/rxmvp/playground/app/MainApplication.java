package br.ufs.demos.rxmvp.playground.app;

import android.app.Activity;
import android.app.Application;

import javax.inject.Inject;

import br.ufs.demos.rxmvp.playground.app.di.DaggerAppComponent;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by bira on 6/26/17.
 */

public class MainApplication extends Application implements HasActivityInjector {

    @Inject DispatchingAndroidInjector<Activity> injector;

    @Override
    public void onCreate() {
        super.onCreate();
        buildTopLevelDependenciesGraph();
    }

    @Override public DispatchingAndroidInjector<Activity> activityInjector() {
        return injector;
    }

    private void buildTopLevelDependenciesGraph() {
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this);
    }

}
