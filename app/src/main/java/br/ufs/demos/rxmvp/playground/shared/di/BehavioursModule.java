package br.ufs.demos.rxmvp.playground.shared.di;

import br.ufs.demos.rxmvp.playground.app.di.qualifiers.UIScheduler;
import br.ufs.demos.rxmvp.playground.shared.BehavioursCoordinator;
import br.ufs.demos.rxmvp.playground.shared.emptystate.AssignEmptyState;
import br.ufs.demos.rxmvp.playground.shared.emptystate.EmptyStateView;
import br.ufs.demos.rxmvp.playground.shared.errorstate.AssignErrorState;
import br.ufs.demos.rxmvp.playground.shared.errorstate.ErrorStateView;
import br.ufs.demos.rxmvp.playground.shared.loadingcontent.LoadingCoordination;
import br.ufs.demos.rxmvp.playground.shared.loadingcontent.LoadingView;
import br.ufs.demos.rxmvp.playground.shared.networking.NetworkingErrorView;
import br.ufs.demos.rxmvp.playground.shared.networking.NetworkingErrorFeedback;
import br.ufs.demos.rxmvp.playground.shared.tooglerefresh.RefreshToogle;
import br.ufs.demos.rxmvp.playground.shared.tooglerefresh.ToogleRefreshView;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

/**
 * Created by bira on 7/5/17.
 */

@Module
public class BehavioursModule {

    @Provides static BehavioursCoordinator coordinator(
            AssignEmptyState assignEmptyState,
            AssignErrorState assignErrorState,
            LoadingCoordination loadingCoordination,
            NetworkingErrorFeedback networkingErrorFeedback,
            RefreshToogle refreshToogle) {

        return new BehavioursCoordinator(
                assignEmptyState,
                assignErrorState,
                loadingCoordination,
                networkingErrorFeedback,
                refreshToogle);
    }


    @Provides static RefreshToogle refreshToogle(ToogleRefreshView view,
                                                 @UIScheduler Scheduler scheduler) {
        return new RefreshToogle(view, scheduler);
    }

    @Provides static LoadingCoordination loadingCoordinator(LoadingView view,
                                                            @UIScheduler Scheduler scheduler) {
        return new LoadingCoordination(view, scheduler);
    }

    @Provides static AssignErrorState assignErrorState(ErrorStateView view,
                                                       @UIScheduler Scheduler scheduler) {
        return new AssignErrorState(view, scheduler);
    }

    @Provides static AssignEmptyState coordinateEmptyState(EmptyStateView view,
                                                           @UIScheduler Scheduler scheduler) {
        return new AssignEmptyState(view, scheduler);
    }

    @Provides static NetworkingErrorFeedback networkingFeedback(NetworkingErrorView view,
                                                                @UIScheduler Scheduler scheduler) {
        return new NetworkingErrorFeedback(view, scheduler);
    }

}
