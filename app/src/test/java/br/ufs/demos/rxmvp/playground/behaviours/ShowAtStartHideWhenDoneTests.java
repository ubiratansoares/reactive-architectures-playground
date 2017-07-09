package br.ufs.demos.rxmvp.playground.behaviours;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import br.ufs.demos.rxmvp.playground.core.ShowAtStartHideWhenDone;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

import static br.ufs.demos.rxmvp.playground.util.MockitoHelpers.oneTimeOnly;
import static org.mockito.Mockito.verify;

/**
 * Created by bira on 6/30/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class ShowAtStartHideWhenDoneTests {

    Scheduler targetScheduler = Schedulers.trampoline();
    @Mock Action whenStart;
    @Mock Action whenDone;

    @Before public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test public void shouldHideAtStart_AndNotShowError_WhenFlowEmmits() throws Exception {
        Flowable.just("AB", "CD")
                .compose(new ShowAtStartHideWhenDone<>(whenStart, whenDone, targetScheduler))
                .subscribe();

        verifyShowAndHideWithFlow();
    }

    @Test public void shouldHideAtStart_AndNotShowError_WithEmptyFlow() throws Exception {
        Flowable.empty()
                .compose(new ShowAtStartHideWhenDone<>(whenStart, whenDone, targetScheduler))
                .subscribe();

        verifyShowAndHideWithFlow();
    }

    @Test public void shouldHideAtStart_AndNotShowError_WithErrorFlow() throws Exception {
        Flowable.error(new RuntimeException("Damn it!!!"))
                .compose(new ShowAtStartHideWhenDone<>(whenStart, whenDone, targetScheduler))
                .subscribe(
                        o -> {},
                        Throwable::printStackTrace,
                        () -> {}
                );

        verifyShowAndHideWithFlow();
    }

    private void verifyShowAndHideWithFlow() throws Exception {
        verify(whenStart, oneTimeOnly()).run();
        verify(whenDone, oneTimeOnly()).run();
    }
}
