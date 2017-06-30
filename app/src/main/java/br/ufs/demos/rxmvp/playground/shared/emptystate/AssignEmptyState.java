package br.ufs.demos.rxmvp.playground.shared.emptystate;

import org.reactivestreams.Publisher;

import br.ufs.demos.rxmvp.playground.shared.internal.HideAtStartShowAtError;
import br.ufs.demos.rxmvp.playground.trivia.domain.errors.ContentNotFoundError;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Scheduler;

/**
 * Created by bira on 6/30/17.
 */

public class AssignEmptyState<T> implements FlowableTransformer<T, T> {

    EmptyStateView view;
    Scheduler uiScheduler;

    public AssignEmptyState(EmptyStateView view, Scheduler uiScheduler) {
        this.view = view;
        this.uiScheduler = uiScheduler;
    }

    @Override public Publisher<T> apply(Flowable<T> upstream) {

        HideAtStartShowAtError<T> delegate = new HideAtStartShowAtError<>(
                view.hideEmptyState(),
                view.showEmptyState(),
                error -> error instanceof ContentNotFoundError,
                uiScheduler
        );

        return upstream.compose(delegate);
    }

}
