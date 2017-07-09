package br.ufs.demos.rxmvp.playground.presentation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.ufs.demos.rxmvp.playground.core.BehavioursCoordinator;
import br.ufs.demos.rxmvp.playground.core.emptystate.AssignEmptyState;
import br.ufs.demos.rxmvp.playground.core.errorstate.AssignErrorState;
import br.ufs.demos.rxmvp.playground.core.lifecyclestrategy.LifecycleStrategist;
import br.ufs.demos.rxmvp.playground.core.loadingcontent.LoadingCoordination;
import br.ufs.demos.rxmvp.playground.core.networking.NetworkingError;
import br.ufs.demos.rxmvp.playground.core.networking.NetworkingErrorFeedback;
import br.ufs.demos.rxmvp.playground.core.tooglerefresh.RefreshToogle;
import br.ufs.demos.rxmvp.playground.trivia.domain.FactAboutNumber;
import br.ufs.demos.rxmvp.playground.trivia.domain.GetRandomFacts;
import br.ufs.demos.rxmvp.playground.trivia.domain.errors.ContentNotFoundError;
import br.ufs.demos.rxmvp.playground.trivia.domain.errors.UnexpectedResponseError;
import br.ufs.demos.rxmvp.playground.trivia.presentation.FactsPresenter;
import br.ufs.demos.rxmvp.playground.trivia.presentation.models.FactViewModel;
import br.ufs.demos.rxmvp.playground.trivia.presentation.models.NumberAndFact;
import br.ufs.demos.rxmvp.playground.util.BehavioursRobot;
import br.ufs.demos.rxmvp.playground.util.DataFlowWatcher;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by bira on 6/30/17.
 */

public class FactsPresenterTests {

    FakeDisplayFactsView view;
    FactsPresenter presenter;

    @Mock LifecycleStrategist strategist;
    @Mock GetRandomFacts usecase;
    @Mock Consumer<FactViewModel> onNext;
    @Mock Consumer<Throwable> onError;
    @Mock Action onCompleted;

    @Before public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
        Scheduler uiScheduler = Schedulers.trampoline();

        view = new FakeDisplayFactsView(onNext, onError, onCompleted);

        BehavioursCoordinator<FactAboutNumber> coordinator = new BehavioursCoordinator<>(
                new AssignEmptyState<>(view, uiScheduler),
                new AssignErrorState<>(view, uiScheduler),
                new LoadingCoordination<>(view, uiScheduler),
                new NetworkingErrorFeedback<>(view, uiScheduler),
                new RefreshToogle<>(view, uiScheduler)
        );

        presenter = new FactsPresenter(
                usecase,
                view,
                coordinator,
                strategist,
                domainData -> new NumberAndFact(domainData.number, domainData.fact)
        );
    }

    @Test public void shouldPresent_AvailableData_IntoView() throws Exception {

        Flowable<FactAboutNumber> data = Flowable.just(
                FactAboutNumber.of("1", "1 is the first"),
                FactAboutNumber.of("2", "2 is the second")
        );

        when(usecase.fetchTrivia()).thenReturn(data);

        presenter.fetchRandomFacts();

        BehavioursRobot.with(view)
                .showLoadingFirstHideLoadingAfter()
                .shouldNotShowEmptyState()
                .shouldNotShowErrorState()
                .shouldNotReportNetworkingError();

        DataFlowWatcher.with(onNext, onError, onCompleted).shouldReceiveItems(2);

        verify(strategist, atLeastOnce()).applyStrategy(any());
    }

    @Test public void shouldPresent_NoContentError_IntoView() throws Exception {

        Flowable<FactAboutNumber> noContent = Flowable.error(new ContentNotFoundError());
        when(usecase.fetchTrivia()).thenReturn(noContent);
        presenter.fetchRandomFacts();

        BehavioursRobot.with(view)
                .showLoadingFirstHideLoadingAfter()
                .shouldShowEmptyState()
                .shouldNotShowErrorState()
                .shouldNotReportNetworkingError();


        DataFlowWatcher.with(onNext, onError, onCompleted).shouldFinishWithError();

        verify(strategist, atLeastOnce()).applyStrategy(any());
    }

    @Test public void shouldPresent_UnexpectedResponseError_IntoView() throws Exception {

        Flowable<FactAboutNumber> error5xx = Flowable.error(new UnexpectedResponseError("503"));
        when(usecase.fetchTrivia()).thenReturn(error5xx);
        presenter.fetchRandomFacts();

        BehavioursRobot.with(view)
                .showLoadingFirstHideLoadingAfter()
                .shouldNotShowEmptyState()
                .shouldShowErrorState()
                .shouldNotReportNetworkingError();


        DataFlowWatcher.with(onNext, onError, onCompleted).shouldFinishWithError();

        verify(strategist, atLeastOnce()).applyStrategy(any());
    }

    @Test public void shouldPresent_AnotherError_IntoView() throws Exception {

        Flowable<FactAboutNumber> broken = Flowable.error(new IllegalAccessError("WTF!"));
        when(usecase.fetchTrivia()).thenReturn(broken);

        presenter.fetchRandomFacts();

        BehavioursRobot.with(view)
                .showLoadingFirstHideLoadingAfter()
                .shouldShowErrorState()
                .shouldNotShowEmptyState()
                .shouldNotReportNetworkingError();


        DataFlowWatcher.with(onNext, onError, onCompleted).shouldFinishWithError();
        verify(strategist, atLeastOnce()).applyStrategy(any());
    }

    @Test public void shouldReport_NetworkingError_IntoView() throws Exception {

        Flowable<FactAboutNumber> broken = Flowable.error(new NetworkingError("Timeout"));
        when(usecase.fetchTrivia()).thenReturn(broken);

        presenter.fetchRandomFacts();

        BehavioursRobot.with(view)
                .showLoadingFirstHideLoadingAfter()
                .shouldShowErrorState()
                .shouldNotShowEmptyState()
                .shouldReportNetworkingError();


        DataFlowWatcher.with(onNext, onError, onCompleted).shouldFinishWithError();
        verify(strategist, atLeastOnce()).applyStrategy(any());
    }
}
