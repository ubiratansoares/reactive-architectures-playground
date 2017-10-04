package br.ufs.demos.rxmvp.playground.app;

import android.app.Activity;
import android.app.Application;

import javax.inject.Inject;

import br.ufs.demos.rxmvp.playground.dagger.components.DaggerAppComponent;
import br.ufs.demos.rxmvp.playground.fakes.FakeWebService;
import br.ufs.demos.rxmvp.playground.fakes.FakeWebService.NextCall;
import br.ufs.demos.rxmvp.playground.webservice.NumbersWebService;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by bira on 6/26/17.
 */

public class MainApplication extends Application implements HasActivityInjector {

    @Inject DispatchingAndroidInjector<Activity> injector;
    @Inject public NumbersWebService webservice;

    @Override public void onCreate() {
        super.onCreate();
        buildTopLevelDependenciesGraph();
        setupMock();
    }

    @Override public DispatchingAndroidInjector<Activity> activityInjector() {
        return injector;
    }

    protected void buildTopLevelDependenciesGraph() {
        DaggerAppComponent.builder().application(this).build().inject(this);
    }

    private void setupMock() {
        FakeWebService service = (FakeWebService) webservice;
        service.nextCallPerform(NextCall.SUCCESS_200);
    }

}
