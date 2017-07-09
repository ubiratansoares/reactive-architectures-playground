package br.ufs.demos.rxmvp.playground.trivia.presentation;

import br.ufs.demos.rxmvp.playground.core.emptystate.EmptyStateView;
import br.ufs.demos.rxmvp.playground.core.errorstate.ErrorStateView;
import br.ufs.demos.rxmvp.playground.core.loadingcontent.LoadingView;
import br.ufs.demos.rxmvp.playground.core.networking.NetworkingErrorView;
import br.ufs.demos.rxmvp.playground.core.tooglerefresh.ToogleRefreshView;
import br.ufs.demos.rxmvp.playground.trivia.presentation.models.FactViewModel;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

/**
 * Created by bira on 6/29/17.
 */

public interface DisplayFactsView extends
        LoadingView,
        ErrorStateView,
        EmptyStateView,
        NetworkingErrorView,
        ToogleRefreshView {

    Disposable subscribeInto(Flowable<FactViewModel> flow);

}
