package br.ufs.demos.rxmvp.playground.core.behaviours.networking;

import org.reactivestreams.Publisher;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;


/**
 * Created by bira on 7/5/17.
 */

public class NetworkingErrorHandler<T> implements FlowableTransformer<T, T> {

    @Override public Publisher<T> apply(Flowable<T> upstream) {
        return upstream.onErrorResumeNext(this::handleIfNetworkingError);
    }

    private Publisher<T> handleIfNetworkingError(Throwable throwable) {
        if (isNetworkingError(throwable)) return asNetworkingError(throwable);
        return Flowable.error(throwable);
    }

    private Flowable<T> asNetworkingError(Throwable throwable) {
        String failure = failureFrom(throwable);
        return Flowable.error(new NetworkingError(failure));
    }

    private String failureFrom(Throwable throwable) {
        if (isConnectionTimeout(throwable)) {
            return "Connection timeout";

        } else if (isRequestCanceled(throwable)) {
            return "Connection interrupted error";

        } else if (noInternetAvailable(throwable)) {
            return "No internet available";
        }

        return "Unknown networking error";
    }

    private boolean isNetworkingError(Throwable throwable) {
        return isConnectionTimeout(throwable) ||
                noInternetAvailable(throwable) ||
                isRequestCanceled(throwable);
    }

    private boolean isRequestCanceled(Throwable throwable) {
        return throwable instanceof IOException
                && throwable.getMessage().contentEquals("Canceled");
    }

    private boolean noInternetAvailable(Throwable throwable) {
        return throwable instanceof UnknownHostException;
    }

    private boolean isConnectionTimeout(Throwable throwable) {
        return throwable instanceof SocketTimeoutException;
    }

}
