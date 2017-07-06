package br.ufs.demos.rxmvp.playground.app.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import br.ufs.demos.rxmvp.playground.app.di.qualifiers.IOScheduler;
import br.ufs.demos.rxmvp.playground.app.di.qualifiers.UIScheduler;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bira on 6/26/17.
 */

@Module
public class AppModule {

    @Provides @Singleton static Context provideContext(Application application) {
        return application;
    }

    @Provides @Singleton @UIScheduler static Scheduler uiScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides @Singleton @IOScheduler static Scheduler ioScheduler() {
        return Schedulers.io();
    }

}
