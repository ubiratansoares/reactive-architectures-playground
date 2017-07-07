package br.ufs.demos.rxmvp.playground.trivia.di;

import br.ufs.demos.rxmvp.playground.shared.BehavioursCoordinator;
import br.ufs.demos.rxmvp.playground.shared.lifecyclestrategy.LifecycleStrategist;
import br.ufs.demos.rxmvp.playground.trivia.domain.GetRandomFacts;
import br.ufs.demos.rxmvp.playground.trivia.presentation.DisplayFactsView;
import br.ufs.demos.rxmvp.playground.trivia.presentation.FactsPresenter;
import br.ufs.demos.rxmvp.playground.trivia.presentation.ViewModelMapper;
import dagger.Module;
import dagger.Provides;

/**
 * Created by bira on 6/26/17.
 */

@Module(includes = InfrastructureModule.class)
public class PresentationModule {

    @Provides static FactsPresenter presenter(GetRandomFacts usecase,
                                              DisplayFactsView view,
                                              BehavioursCoordinator coordinator,
                                              LifecycleStrategist strategist,
                                              ViewModelMapper mapper) {

        return new FactsPresenter(usecase, view, coordinator, strategist, mapper);
    }

    @Provides static ViewModelMapper viewModelMapper() {
        return new ViewModelMapper();
    }
}
