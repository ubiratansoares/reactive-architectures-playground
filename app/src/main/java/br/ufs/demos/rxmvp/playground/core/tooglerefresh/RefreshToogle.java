package br.ufs.demos.rxmvp.playground.core.tooglerefresh;

import org.reactivestreams.Publisher;

import br.ufs.demos.rxmvp.playground.trivia.domain.errors.ContentNotFoundError;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;

/**
 * Created by bira on 7/8/17.
 */

public class RefreshToogle<T> implements FlowableTransformer<T, T> {

    private final ToogleRefreshView view;
    private Scheduler targetScheduler;

    public RefreshToogle(ToogleRefreshView view, Scheduler targetScheduler) {
        this.view = view;
        this.targetScheduler = targetScheduler;
    }

    @Override public Publisher<T> apply(Flowable<T> upstream) {
        return upstream
                .doOnSubscribe(subscription -> fireAction(view.disableRefresh()))
                .doOnError(this::enableForSpecialErrorCase)
                .doOnComplete(() -> fireAction(view.enableRefresh()));
    }

    private void enableForSpecialErrorCase(Throwable throwable) {
        if (throwable instanceof ContentNotFoundError) fireAction(view.enableRefresh());
    }

    private void fireAction(Action toPerform) {
        Completable.fromAction(toPerform)
                .subscribeOn(targetScheduler)
                .subscribe();
    }

}
