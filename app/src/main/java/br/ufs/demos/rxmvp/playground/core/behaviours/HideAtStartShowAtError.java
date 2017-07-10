package br.ufs.demos.rxmvp.playground.core.behaviours;

import org.reactivestreams.Publisher;

import br.ufs.demos.rxmvp.playground.util.ErrorPredicate;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;

/**
 * Created by bira on 6/30/17.
 */

public class HideAtStartShowAtError<T> implements FlowableTransformer<T, T> {

    private Action whenStart;
    private Action atError;
    private ErrorPredicate errorPredicate;
    private Scheduler targetScheduler;

    public HideAtStartShowAtError(Action whenStart,
                                  Action atError,
                                  ErrorPredicate errorPredicate,
                                  Scheduler targetScheduler) {

        this.whenStart = whenStart;
        this.atError = atError;
        this.errorPredicate = errorPredicate;
        this.targetScheduler = targetScheduler;
    }

    @Override public Publisher<T> apply(Flowable<T> upstream) {
        return upstream
                .doOnSubscribe(subscription -> hide())
                .doOnError(this::evaluateAndShowIfApplicable);
    }

    private void evaluateAndShowIfApplicable(Throwable throwable) {
        if (errorPredicate.evaluate(throwable)) {
            subscribeAndFireAction(atError);
        }
    }

    private void hide() {
        subscribeAndFireAction(whenStart);
    }

    private void subscribeAndFireAction(Action toPerform) {
        Completable.fromAction(toPerform)
                .subscribeOn(targetScheduler)
                .subscribe();
    }

}
