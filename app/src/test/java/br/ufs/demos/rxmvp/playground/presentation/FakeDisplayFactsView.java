package br.ufs.demos.rxmvp.playground.presentation;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.ufs.demos.rxmvp.playground.trivia.presentation.DisplayFactsView;
import br.ufs.demos.rxmvp.playground.trivia.presentation.FactViewModel;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by bira on 6/30/17.
 */

public class FakeDisplayFactsView implements DisplayFactsView {

    @Mock private Action showEmptyAction;
    @Mock private Action hideEmptyAction;
    @Mock private Action showErrorAction;
    @Mock private Action hideErrorAction;
    @Mock private Action showLoadingAction;
    @Mock private Action hideLoadingAction;

    private Consumer<FactViewModel> modelConsumer;
    private Consumer<Throwable> errorConsumer;
    private Action done;

    public FakeDisplayFactsView(Consumer<FactViewModel> modelConsumer,
                                Consumer<Throwable> errorConsumer,
                                Action done) {

        MockitoAnnotations.initMocks(this);
        this.modelConsumer = modelConsumer;
        this.errorConsumer = errorConsumer;
        this.done = done;
    }

    @Override public Action showEmptyState() {
        return showEmptyAction;
    }

    @Override public Action hideEmptyState() {
        return hideEmptyAction;
    }

    @Override public Action showErrorState() {
        return showErrorAction;
    }

    @Override public Action hideErrorState() {
        return hideErrorAction;
    }

    @Override public Action showLoading() {
        return showLoadingAction;
    }

    @Override public Action hideLoading() {
        return hideLoadingAction;
    }

    @Override public Disposable subscribeInto(Flowable<FactViewModel> flow) {
        return flow.subscribe(modelConsumer, errorConsumer, done);
    }
}
