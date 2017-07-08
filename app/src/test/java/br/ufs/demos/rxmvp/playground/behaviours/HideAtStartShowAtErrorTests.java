package br.ufs.demos.rxmvp.playground.behaviours;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import br.ufs.demos.rxmvp.playground.shared.HideAtStartShowAtError;
import br.ufs.demos.rxmvp.playground.util.ErrorPredicate;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

import static br.ufs.demos.rxmvp.playground.util.MockitoHelpers.oneTimeOnly;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Created by bira on 6/30/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class HideAtStartShowAtErrorTests {

    Scheduler scheduler = Schedulers.trampoline();
    ErrorPredicate positive = error -> true;
    ErrorPredicate negative = error -> false;

    @Mock Action whenStart;
    @Mock Action atError;

    @Before public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test public void shouldHideAtStart_AndNotShowError_WhenFlowEmmits() throws Exception {
        Flowable.just("A", "B", "C")
                .compose(new HideAtStartShowAtError<>(whenStart, atError, positive, scheduler))
                .subscribe();

        verify(whenStart, oneTimeOnly()).run();
        verifyZeroInteractions(atError);
    }

    @Test public void shouldHideAtStart_AndNotShowError_WhenFlowIsEmpty() throws Exception {
        Flowable.empty()
                .compose(new HideAtStartShowAtError<>(whenStart, atError, positive, scheduler))
                .subscribe();

        verify(whenStart, oneTimeOnly()).run();
        verifyZeroInteractions(atError);
    }

    @Test public void shouldHideAtStart_AndNotShowError_WhenEvaluationFails() throws Exception {
        Flowable.error(new RuntimeException("Ouch"))
                .compose(new HideAtStartShowAtError<>(whenStart, atError, negative, scheduler))
                .subscribe(
                        o -> {},
                        Throwable::printStackTrace,
                        () -> {}
                );

        verify(whenStart, oneTimeOnly()).run();
        verifyZeroInteractions(atError);

    }

    @Test public void shouldHideAtStart_AndShowError_WhenEvaluationSuccessful() throws Exception {
        Flowable.error(new RuntimeException("Ouch Again"))
                .compose(new HideAtStartShowAtError<>(whenStart, atError, positive, scheduler))
                .subscribe(
                        o -> {},
                        Throwable::printStackTrace,
                        () -> {}
                );

        verify(whenStart, oneTimeOnly()).run();
        verify(atError, oneTimeOnly()).run();

    }
}
