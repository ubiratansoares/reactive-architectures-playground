package br.ufs.demos.rxmvp.playground.app.di;

import android.app.Application;

import javax.inject.Singleton;

import br.ufs.demos.rxmvp.playground.app.MainApplication;
import br.ufs.demos.rxmvp.playground.webservice.di.RestWebServiceModule;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by bira on 6/26/17.
 */

@Singleton
@Component(
        modules = {
                AndroidInjectionModule.class,
                AppModule.class,
                RestWebServiceModule.class,
                ActivitiesBuilder.class
        }
)
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance Builder application(Application application);

        AppComponent build();
    }

    void inject(MainApplication target);
}