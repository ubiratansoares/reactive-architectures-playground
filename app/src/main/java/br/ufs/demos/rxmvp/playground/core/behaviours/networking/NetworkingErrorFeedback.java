package br.ufs.demos.rxmvp.playground.core.behaviours.networking;

import org.reactivestreams.Publisher;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Scheduler;

/**
 * Created by bira on 7/5/17.
 */

public class NetworkingErrorFeedback<T> implements FlowableTransformer<T, T> {

    private NetworkingErrorView view;
    private Scheduler uiScheduler;

    public NetworkingErrorFeedback(NetworkingErrorView view, Scheduler uiScheduler) {
        this.view = view;
        this.uiScheduler = uiScheduler;
    }

    @Override public Publisher<T> apply(Flowable<T> upstream) {
        return upstream.doOnError(this::handleIfNetworkingError);
    }

    private Publisher<T> handleIfNetworkingError(Throwable throwable) {

        if (throwable instanceof NetworkingError) {
            Completable.fromAction(view.reportNetworkingError())
                    .observeOn(uiScheduler)
                    .subscribe();
        }

        return Flowable.error(throwable);
    }

}
