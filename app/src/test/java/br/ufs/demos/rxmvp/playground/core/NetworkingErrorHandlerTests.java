package br.ufs.demos.rxmvp.playground.core;

import com.google.gson.JsonSyntaxException;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import br.ufs.demos.rxmvp.playground.core.behaviours.networking.NetworkingError;
import br.ufs.demos.rxmvp.playground.core.behaviours.networking.NetworkingErrorHandler;
import io.reactivex.Flowable;

/**
 * Created by bira on 7/6/17.
 */

public class NetworkingErrorHandlerTests {

    NetworkingErrorHandler handler;

    @Before public void beforeEachTest() {
        handler = new NetworkingErrorHandler();
    }

    @Test public void shouldHandle_NoInternetAvailable() {
        Flowable<?> noInternet = Flowable.error(new UnknownHostException("No Internet"));
        noInternet.compose(handler)
                .test()
                .assertError(NetworkingError.class);
    }

    @Test public void shouldHandle_ConnectionError() {
        Flowable<?> brokenConnection = Flowable.error(new IOException("Canceled"));
        brokenConnection.compose(handler)
                .test()
                .assertError(NetworkingError.class);
    }

    @Test public void shouldHandle_SocketTimeout() {
        Flowable<?> timeout = Flowable.error(new SocketTimeoutException());
        timeout.compose(handler)
                .test()
                .assertError(NetworkingError.class);
    }

    @Test public void shouldNotHandle_NonNetworkingError() {
        Flowable<?> otherError = Flowable.error(new JsonSyntaxException("Broken json"));
        otherError.compose(handler)
                .test()
                .assertError(throwable -> !(throwable instanceof NetworkingError));
    }

}
