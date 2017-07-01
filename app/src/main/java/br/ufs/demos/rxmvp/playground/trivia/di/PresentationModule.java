package br.ufs.demos.rxmvp.playground.trivia.di;

import br.ufs.demos.rxmvp.playground.app.di.qualifiers.UIScheduler;
import br.ufs.demos.rxmvp.playground.shared.BehavioursCoordinator;
import br.ufs.demos.rxmvp.playground.shared.DisposeStrategy;
import br.ufs.demos.rxmvp.playground.shared.LifecycleStrategist;
import br.ufs.demos.rxmvp.playground.shared.emptystate.AssignEmptyState;
import br.ufs.demos.rxmvp.playground.shared.errorstate.AssignErrorState;
import br.ufs.demos.rxmvp.playground.shared.loadingcontent.LoadingCoordination;
import br.ufs.demos.rxmvp.playground.trivia.FactsAboutNumbersActivity;
import br.ufs.demos.rxmvp.playground.trivia.domain.GetRandomFacts;
import br.ufs.demos.rxmvp.playground.trivia.presentation.FactsPresenter;
import br.ufs.demos.rxmvp.playground.trivia.presentation.ViewModelMapper;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

/**
 * Created by bira on 6/26/17.
 */

@Module(includes = InfrastructureModule.class)
public class PresentationModule {

    @Provides static FactsPresenter presenter(GetRandomFacts usecase,
                                              FactsAboutNumbersActivity view,
                                              BehavioursCoordinator coordinator,
                                              LifecycleStrategist strategist,
                                              ViewModelMapper mapper) {

        return new FactsPresenter(usecase, view, coordinator, strategist, mapper);
    }

    @Provides static LifecycleStrategist strategist(FactsAboutNumbersActivity activity) {
        return new LifecycleStrategist(activity, new DisposeStrategy());
    }

    @Provides static BehavioursCoordinator coordinator(
            AssignEmptyState assignEmptyState,
            AssignErrorState assignErrorState,
            LoadingCoordination loadingCoordination) {

        return new BehavioursCoordinator(assignEmptyState, assignErrorState, loadingCoordination);
    }

    @Provides static LoadingCoordination loadingCoordinator(FactsAboutNumbersActivity view,
                                                            @UIScheduler Scheduler scheduler) {
        return new LoadingCoordination(view, scheduler);
    }

    @Provides static AssignErrorState assignErrorState(FactsAboutNumbersActivity view,
                                                       @UIScheduler Scheduler scheduler) {
        return new AssignErrorState(view, scheduler);
    }

    @Provides static AssignEmptyState coordinateEmptyState(FactsAboutNumbersActivity view,
                                                           @UIScheduler Scheduler scheduler) {
        return new AssignEmptyState(view, scheduler);
    }

    @Provides static ViewModelMapper viewModelMapper() {
        return new ViewModelMapper();
    }
}
