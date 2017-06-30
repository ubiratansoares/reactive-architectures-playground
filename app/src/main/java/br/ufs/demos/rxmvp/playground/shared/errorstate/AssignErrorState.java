package br.ufs.demos.rxmvp.playground.shared.errorstate;

import org.reactivestreams.Publisher;

import br.ufs.demos.rxmvp.playground.shared.internal.HideAtStartShowAtError;
import br.ufs.demos.rxmvp.playground.trivia.domain.errors.UnexpectedResponseError;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Scheduler;

/**
 * Created by bira on 6/30/17.
 */

public class AssignErrorState<T> implements FlowableTransformer<T, T> {

    ErrorStateView view;
    Scheduler uiScheduler;

    public AssignErrorState(ErrorStateView view, Scheduler uiScheduler) {
        this.view = view;
        this.uiScheduler = uiScheduler;
    }

    @Override public Publisher<T> apply(Flowable<T> upstream) {
        HideAtStartShowAtError<T> delegate = new HideAtStartShowAtError<>(
                view.hideErrorState(),
                view.showErrorState(),
                error -> error instanceof UnexpectedResponseError,
                uiScheduler
        );

        return upstream.compose(delegate);
    }

}
