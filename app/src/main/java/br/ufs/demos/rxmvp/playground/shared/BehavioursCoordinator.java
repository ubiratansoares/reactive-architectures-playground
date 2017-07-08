package br.ufs.demos.rxmvp.playground.shared;

import org.reactivestreams.Publisher;

import br.ufs.demos.rxmvp.playground.shared.emptystate.AssignEmptyState;
import br.ufs.demos.rxmvp.playground.shared.errorstate.AssignErrorState;
import br.ufs.demos.rxmvp.playground.shared.loadingcontent.LoadingCoordination;
import br.ufs.demos.rxmvp.playground.shared.networking.NetworkingErrorFeedback;
import br.ufs.demos.rxmvp.playground.shared.tooglerefresh.RefreshToogle;
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
