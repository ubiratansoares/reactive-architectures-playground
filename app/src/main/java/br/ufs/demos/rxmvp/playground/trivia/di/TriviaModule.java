package br.ufs.demos.rxmvp.playground.trivia.di;

import br.ufs.demos.rxmvp.playground.app.di.UIScheduler;
import br.ufs.demos.rxmvp.playground.shared.BehavioursCoordinator;
import br.ufs.demos.rxmvp.playground.shared.DisposeStrategy;
import br.ufs.demos.rxmvp.playground.shared.LifecycleStrategist;
import br.ufs.demos.rxmvp.playground.shared.emptystate.AssignEmptyState;
import br.ufs.demos.rxmvp.playground.shared.errorstate.AssignErrorState;
import br.ufs.demos.rxmvp.playground.shared.loadingcontent.LoadingBehaviour;
import br.ufs.demos.rxmvp.playground.trivia.FactsAboutNumbersActivity;
import br.ufs.demos.rxmvp.playground.trivia.presentation.FactsPresenter;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

/**
 * Created by bira on 6/26/17.
 */

@Module
public class TriviaModule {

    @Provides static FactsPresenter presenter(FactsAboutNumbersActivity view,
                                              BehavioursCoordinator behavioursCoordinator,
                                              LifecycleStrategist strategist) {

        return new FactsPresenter(view, behavioursCoordinator, strategist);
    }

    @Provides static LifecycleStrategist strategist(FactsAboutNumbersActivity activity) {
        return new LifecycleStrategist(activity, new DisposeStrategy());
    }

    @Provides static BehavioursCoordinator coordinator(
            AssignEmptyState assignEmptyState,
            AssignErrorState assignErrorState,
            LoadingBehaviour loadingCoordinator) {

        return new BehavioursCoordinator(
                assignEmptyState,
                assignErrorState,
                loadingCoordinator
        );
    }

    @Provides static LoadingBehaviour loadingCoordinator(
            FactsAboutNumbersActivity view,
            @UIScheduler Scheduler scheduler) {
        return new LoadingBehaviour(view, scheduler);
    }

    @Provides static AssignErrorState assignErrorState(
            FactsAboutNumbersActivity view,
            @UIScheduler Scheduler scheduler) {
        return new AssignErrorState(view, scheduler);
    }

    @Provides static AssignEmptyState coordinateEmptyState(
            FactsAboutNumbersActivity view,
            @UIScheduler Scheduler scheduler) {
        return new AssignEmptyState(view, scheduler);
    }
}
