package br.ufs.demos.rxmvp.playground.shared.loadingcontent;

import org.reactivestreams.Publisher;

import br.ufs.demos.rxmvp.playground.shared.internal.ShowAtStartHideWhenFinish;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Scheduler;

/**
 * Created by bira on 6/29/17.
 */

public class LoadingBehaviour<T> implements FlowableTransformer<T, T> {

    private LoadingView view;
    private Scheduler uiScheduler;

    public LoadingBehaviour(LoadingView view,
                            Scheduler uiScheduler) {
        this.view = view;
        this.uiScheduler = uiScheduler;
    }

    @Override public Publisher<T> apply(Flowable<T> upstream) {

        ShowAtStartHideWhenFinish<T> delegate = new ShowAtStartHideWhenFinish<>(
                view.showLoading(),
                view.hideLoading(),
                uiScheduler
        );

        return upstream.compose(delegate);
    }

}
