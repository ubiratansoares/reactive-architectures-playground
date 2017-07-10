package br.ufs.demos.rxmvp.playground.core;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import br.ufs.demos.rxmvp.playground.core.behaviours.errorstate.AssignErrorState;
import br.ufs.demos.rxmvp.playground.core.behaviours.errorstate.ErrorStateView;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

import static br.ufs.demos.rxmvp.playground.util.MockitoHelpers.oneTimeOnly;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by bira on 6/30/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class AssignErrorStateTests {

    Scheduler uiScheduler = Schedulers.trampoline();
    AssignErrorState<String> assignErrorState;

    @Mock Action show;
    @Mock Action hide;

    @Before public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);

        ErrorStateView view = new ErrorStateView() {
            @Override public Action showErrorState() {
                return show;
            }

            @Override public Action hideErrorState() {
                return hide;
            }
        };

        assignErrorState = new AssignErrorState<>(view, uiScheduler);
    }

    @Test public void shouldNotAssignError_WhenFlowEmmits() throws Exception {
        Flowable.just("A", "B")
                .compose(assignErrorState)
                .subscribe();

        verify(hide, oneTimeOnly()).run();
        verify(show, never()).run();
    }

    @Test public void shouldNotAssignError_WithEmptyFlow() throws Exception {
        Flowable<String> empty = Flowable.empty();
        empty.compose(assignErrorState).subscribe();

        verify(hide, oneTimeOnly()).run();
        verify(show, never()).run();
    }

    @Test public void shouldAssignError_WithErrorAtFlow() throws Exception {
        Flowable<String> broken = Flowable.error(new RuntimeException("WTF!!"));

        broken.compose(assignErrorState)
                .subscribe(
                        some -> {},
                        Throwable::printStackTrace,
                        () -> {}
                );

        verify(hide, oneTimeOnly()).run();
        verify(show, oneTimeOnly()).run();
    }

}
