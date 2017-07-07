package br.ufs.demos.rxmvp.playground.webservice.di;

import javax.inject.Singleton;

import br.ufs.demos.rxmvp.playground.webservice.NumbersWebService;
import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bira on 6/26/17.
 */

@Module
public class RestWebServiceModule {

    @Provides @Singleton static NumbersWebService webService(OkHttpClient customHttpClient) {

        Retrofit adapter = new Retrofit.Builder()
                .baseUrl(NumbersWebService.BASE_URL)
                .client(customHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return adapter.create(NumbersWebService.class);
    }

    @Provides @Singleton static OkHttpClient httpClient(Interceptor logger) {

        return new OkHttpClient.Builder()
                .addInterceptor(logger)
                .build();
    }

    @Provides @Singleton static Interceptor logger() {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return logger;
    }
}
