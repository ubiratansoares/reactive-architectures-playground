package br.ufs.demos.rxmvp.playground.shared;

import br.ufs.demos.rxmvp.playground.shared.emptystate.AssignEmptyState;
import br.ufs.demos.rxmvp.playground.shared.errorstate.AssignErrorState;
import br.ufs.demos.rxmvp.playground.shared.loadingcontent.LoadingCoordination;
import io.reactivex.Flowable;

/**
 * Created by bira on 6/30/17.
 */

public class BehavioursCoordinator<T> {

    private AssignEmptyState<T> dealWithEmptyState;
    private AssignErrorState<T> assignErrorState;
    private LoadingCoordination<T> loadingCoordinator;

    public BehavioursCoordinator(AssignEmptyState<T> dealWithEmptyState,
                                 AssignErrorState<T> assignErrorState,
                                 LoadingCoordination<T> loadingCoordinator) {

        this.dealWithEmptyState = dealWithEmptyState;
        this.assignErrorState = assignErrorState;
        this.loadingCoordinator = loadingCoordinator;
    }

    public Flowable<T> coordinateFlow(Flowable<T> upstream) {
        return upstream
                .compose(dealWithEmptyState)
                .compose(assignErrorState)
                .compose(loadingCoordinator);
    }

}
