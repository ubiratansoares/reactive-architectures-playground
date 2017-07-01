package br.ufs.demos.rxmvp.playground.behaviours;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.ufs.demos.rxmvp.playground.shared.emptystate.AssignEmptyState;
import br.ufs.demos.rxmvp.playground.shared.emptystate.EmptyStateView;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

import static br.ufs.demos.rxmvp.playground.util.MockitoHelpers.onlyOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by bira on 6/30/17.
 */

public class AssignEmptyStateTests {

    Scheduler uiScheduler = Schedulers.trampoline();
    AssignEmptyState<Integer> assignEmptyness;

    @Mock Action showEmtpyState;
    @Mock Action hideEmtpyState;

    @Before public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);

        EmptyStateView view = new EmptyStateView() {

            @Override public Action showEmptyState() {
                return showEmtpyState;
            }

            @Override public Action hideEmptyState() {
                return hideEmtpyState;
            }
        };

        assignEmptyness = new AssignEmptyState<>(view, uiScheduler);
    }

    @Test public void shouldNotAssignEmpty_WhenFlowEmmits() throws Exception {
        Flowable.just(10, 20, 30)
                .compose(assignEmptyness)
                .subscribe();

        verify(hideEmtpyState, onlyOnce()).run();
        verify(showEmtpyState, never()).run();
    }

    @Test public void shouldNotAssignError_WithEmptyFlow() throws Exception {
        Flowable<Integer> empty = Flowable.empty();
        empty.compose(assignEmptyness).subscribe();

        verify(hideEmtpyState, onlyOnce()).run();
        verify(showEmtpyState, never()).run();
    }

    @Test public void shouldAssignError_WithErrorAtFlow() throws Exception {
        Flowable<Integer> broken = Flowable.error(new RuntimeException("WTF!!"));

        broken.compose(assignEmptyness)
                .subscribe(
                        some -> {},
                        Throwable::printStackTrace,
                        () -> {}
                );

        verify(hideEmtpyState, onlyOnce()).run();
        verify(showEmtpyState, onlyOnce()).run();
    }
}
