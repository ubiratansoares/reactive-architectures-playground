package br.ufs.demos.rxmvp.playground.trivia.presentation;

import java.util.concurrent.TimeUnit;

import br.ufs.demos.rxmvp.playground.shared.BehavioursCoordinator;
import br.ufs.demos.rxmvp.playground.shared.LifecycleStrategist;
import br.ufs.demos.rxmvp.playground.trivia.domain.FactAboutNumber;
import br.ufs.demos.rxmvp.playground.trivia.domain.ImmutableFactAboutNumber;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

/**
 * Created by bira on 6/29/17.
 */

public class FactsPresenter {

    private BehavioursCoordinator<FactAboutNumber> coordinator;
    private DisplayFactsView view;
    private LifecycleStrategist strategist;

    public FactsPresenter(DisplayFactsView view,
                          BehavioursCoordinator coordinator,
                          LifecycleStrategist strategist) {
        this.view = view;
        this.coordinator = coordinator;
        this.strategist = strategist;
    }

    public void fetchRandomFacts() {
        Disposable subscription = view.subscribeWith(dataFlow());
        strategist.applyStrategy(subscription);
    }

    private Flowable<FactViewModel> dataFlow() {
        Flowable<FactAboutNumber> flow = success();

        return coordinator.applyBehaviours(flow)
                .map(factAboutNumber -> new FactViewModel());
    }

    private Flowable<FactAboutNumber> success() {
        return Flowable.just(ImmutableFactAboutNumber.of("1", "2"))
                .cast(FactAboutNumber.class)
                .delay(3, TimeUnit.SECONDS);
    }

}
