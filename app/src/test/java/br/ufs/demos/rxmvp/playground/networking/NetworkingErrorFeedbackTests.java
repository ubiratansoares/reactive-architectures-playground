package br.ufs.demos.rxmvp.playground.networking;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.ufs.demos.rxmvp.playground.core.networking.NetworkingError;
import br.ufs.demos.rxmvp.playground.core.networking.NetworkingErrorFeedback;
import br.ufs.demos.rxmvp.playground.trivia.domain.errors.ContentNotFoundError;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

import static br.ufs.demos.rxmvp.playground.util.MockitoHelpers.oneTimeOnly;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by bira on 7/6/17.
 */

public class NetworkingErrorFeedbackTests {

    Scheduler uiScheduler = Schedulers.trampoline();
    NetworkingErrorFeedback<String> networkingFeedback;

    @Mock Action report;

    @Before public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
        networkingFeedback = new NetworkingErrorFeedback<>(() -> report, uiScheduler);
    }

    @Test public void shouldNotReport_WhenFlowEmmits() throws Exception {
        Flowable.just("A", "B")
                .compose(networkingFeedback)
                .subscribe();

        verify(report, never()).run();
    }

    @Test public void shouldNotReport_WhenFlowIsEmpty() throws Exception {
        Flowable<String> noData = Flowable.empty();
        noData.compose(networkingFeedback).subscribe();

        verify(report, never()).run();
    }

    @Test public void shouldNotReport_ForNonNetworkingError() throws Exception {
        Flowable<String> otherError = Flowable.error(new ContentNotFoundError());
        otherError.compose(networkingFeedback).subscribe();

        verify(report, never()).run();
    }

    @Test public void shoulReport_ForNetworkingError() throws Exception {
        Flowable<String> otherError = Flowable.error(new NetworkingError("Canceled"));
        otherError.compose(networkingFeedback).subscribe();
        verify(report, oneTimeOnly()).run();
    }
}
