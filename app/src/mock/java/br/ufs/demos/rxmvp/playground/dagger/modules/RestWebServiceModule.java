package br.ufs.demos.rxmvp.playground.dagger.modules;

import javax.inject.Singleton;

import br.ufs.demos.rxmvp.playground.fakes.FakeWebService;
import br.ufs.demos.rxmvp.playground.webservice.NumbersWebService;
import dagger.Module;
import dagger.Provides;

/**
 * Created by bira on 6/26/17.
 */

@Module
public class RestWebServiceModule {

    @Provides @Singleton static NumbersWebService webService() {
        return new FakeWebService();
    }

}
