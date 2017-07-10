package br.ufs.demos.rxmvp.playground.trivia.presentation;

import br.ufs.demos.rxmvp.playground.core.behaviours.emptystate.EmptyStateView;
import br.ufs.demos.rxmvp.playground.core.behaviours.errorstate.ErrorStateView;
import br.ufs.demos.rxmvp.playground.core.behaviours.loadingcontent.LoadingView;
import br.ufs.demos.rxmvp.playground.core.behaviours.networking.NetworkingErrorView;
import br.ufs.demos.rxmvp.playground.core.behaviours.tooglerefresh.ToogleRefreshView;
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
