package br.ufs.demos.rxmvp.playground.core.behaviours;

import org.reactivestreams.Publisher;

import br.ufs.demos.rxmvp.playground.core.behaviours.emptystate.AssignEmptyState;
import br.ufs.demos.rxmvp.playground.core.behaviours.errorstate.AssignErrorState;
import br.ufs.demos.rxmvp.playground.core.behaviours.loadingcontent.LoadingCoordination;
import br.ufs.demos.rxmvp.playground.core.behaviours.networking.NetworkingErrorFeedback;
import br.ufs.demos.rxmvp.playground.core.behaviours.tooglerefresh.RefreshToogle;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

/**
 * Created by bira on 6/30/17.
 */

public class BehavioursCoordinator<T> implements FlowableTransformer<T, T> {

    private AssignEmptyState<T> dealWithEmptyState;
    private AssignErrorState<T> assignErrorState;
    private LoadingCoordination<T> loadingCoordinator;
    private NetworkingErrorFeedback<T> networkingErrorFeedback;
    private RefreshToogle<T> refreshToogle;

    public BehavioursCoordinator(AssignEmptyState<T> dealWithEmptyState,
                                 AssignErrorState<T> assignErrorState,
                                 LoadingCoordination<T> loadingCoordinator,
                                 NetworkingErrorFeedback<T> networkingErrorFeedback,
                                 RefreshToogle<T> refreshToogle) {

        this.dealWithEmptyState = dealWithEmptyState;
        this.assignErrorState = assignErrorState;
        this.loadingCoordinator = loadingCoordinator;
        this.networkingErrorFeedback = networkingErrorFeedback;
        this.refreshToogle = refreshToogle;
    }

    @Override public Publisher<T> apply(Flowable<T> upstream) {
        return upstream
                .compose(dealWithEmptyState)
                .compose(assignErrorState)
                .compose(loadingCoordinator)
                .compose(refreshToogle)
                .compose(networkingErrorFeedback);
    }
}
