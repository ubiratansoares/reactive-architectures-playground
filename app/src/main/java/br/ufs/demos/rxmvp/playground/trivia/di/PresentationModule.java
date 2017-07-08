package br.ufs.demos.rxmvp.playground.trivia.di;

import android.content.Context;

import br.ufs.demos.rxmvp.playground.shared.BehavioursCoordinator;
import br.ufs.demos.rxmvp.playground.shared.lifecyclestrategy.LifecycleStrategist;
import br.ufs.demos.rxmvp.playground.trivia.domain.GetRandomFacts;
import br.ufs.demos.rxmvp.playground.trivia.presentation.DisplayFactsView;
import br.ufs.demos.rxmvp.playground.trivia.presentation.FactsPresenter;
import br.ufs.demos.rxmvp.playground.trivia.presentation.FactsViewModelMapper;
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
                                              FactsViewModelMapper mapper) {

        return new FactsPresenter(usecase, view, coordinator, strategist, mapper);
    }

    @Provides static FactsViewModelMapper viewModelMapper(Context context) {
        return new FactsViewModelMapper(context);
    }
}
