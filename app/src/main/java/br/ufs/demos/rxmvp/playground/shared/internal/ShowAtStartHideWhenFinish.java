package br.ufs.demos.rxmvp.playground.shared.internal;

import org.reactivestreams.Publisher;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;

/**
 * Created by bira on 6/29/17.
 */

public class ShowAtStartHideWhenFinish<T> implements FlowableTransformer<T, T> {

    private Action whenStart;
    private Action whenDone;
    private Scheduler targetScheduler;

    public ShowAtStartHideWhenFinish(Action whenStart,
                                     Action whenDone,
                                     Scheduler targetScheduler) {
        this.whenStart = whenStart;
        this.whenDone = whenDone;
        this.targetScheduler = targetScheduler;
    }

    @Override public Publisher<T> apply(Flowable<T> upstream) {
        return upstream
                .doOnSubscribe(subscription -> show())
                .doOnTerminate(this::hide);
    }

    private void show() {
        subscribeAndFireAction(whenStart);
    }

    private void hide() {
        subscribeAndFireAction(whenDone);
    }


    private void subscribeAndFireAction(Action toPerform) {
        Completable.fromAction(toPerform)
                .subscribeOn(targetScheduler)
                .subscribe();
    }

}

