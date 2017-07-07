package br.ufs.demos.rxmvp.playground.shared;

import br.ufs.demos.rxmvp.playground.shared.emptystate.AssignEmptyState;
import br.ufs.demos.rxmvp.playground.shared.errorstate.AssignErrorState;
import br.ufs.demos.rxmvp.playground.shared.loadingcontent.LoadingCoordination;
import br.ufs.demos.rxmvp.playground.shared.networking.NetworkingErrorFeedback;
import io.reactivex.Flowable;

/**
 * Created by bira on 6/30/17.
 */

public class BehavioursCoordinator<T> {

    private AssignEmptyState<T> dealWithEmptyState;
    private AssignErrorState<T> assignErrorState;
    private LoadingCoordination<T> loadingCoordinator;
    private NetworkingErrorFeedback<T> networkingErrorFeedback;

    public BehavioursCoordinator(AssignEmptyState<T> dealWithEmptyState,
                                 AssignErrorState<T> assignErrorState,
                                 LoadingCoordination<T> loadingCoordinator,
                                 NetworkingErrorFeedback<T> networkingErrorFeedback) {

        this.dealWithEmptyState = dealWithEmptyState;
        this.assignErrorState = assignErrorState;
        this.loadingCoordinator = loadingCoordinator;
        this.networkingErrorFeedback = networkingErrorFeedback;
    }

    public Flowable<T> coordinateFlow(Flowable<T> upstream) {
        return upstream
                .compose(dealWithEmptyState)
                .compose(assignErrorState)
                .compose(loadingCoordinator)
                .compose(networkingErrorFeedback);
    }

}
