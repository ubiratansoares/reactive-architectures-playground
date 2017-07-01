package br.ufs.demos.rxmvp.playground.trivia.presentation;

import br.ufs.demos.rxmvp.playground.shared.BehavioursCoordinator;
import br.ufs.demos.rxmvp.playground.shared.LifecycleStrategist;
import br.ufs.demos.rxmvp.playground.trivia.domain.FactAboutNumber;
import br.ufs.demos.rxmvp.playground.trivia.domain.GetRandomFacts;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

/**
 * Created by bira on 6/29/17.
 */

public class FactsPresenter {

    private GetRandomFacts usecase;
    private DisplayFactsView view;
    private BehavioursCoordinator<FactAboutNumber> coordinator;
    private LifecycleStrategist strategist;

    public FactsPresenter(GetRandomFacts usecase,
                          DisplayFactsView view,
                          BehavioursCoordinator coordinator,
                          LifecycleStrategist strategist) {

        this.usecase = usecase;
        this.view = view;
        this.coordinator = coordinator;
        this.strategist = strategist;
    }

    public void fetchRandomFacts() {
        Flowable<FactViewModel> dataFlow =
                coordinator
                        .coordinateFlow(usecase.fetchTrivia())
                        .map(factAboutNumber -> new FactViewModel());

        Disposable lifecycleAware = view.subscribeInto(dataFlow);
        strategist.applyStrategy(lifecycleAware);
    }

}
