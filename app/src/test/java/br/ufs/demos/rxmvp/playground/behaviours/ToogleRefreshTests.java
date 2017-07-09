package br.ufs.demos.rxmvp.playground.behaviours;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.ufs.demos.rxmvp.playground.core.tooglerefresh.RefreshToogle;
import br.ufs.demos.rxmvp.playground.core.tooglerefresh.ToogleRefreshView;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

import static br.ufs.demos.rxmvp.playground.util.MockitoHelpers.oneTimeOnly;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;

/**
 * Created by bira on 7/8/17.
 */

public class ToogleRefreshTests {

    Scheduler uiScheduler = Schedulers.trampoline();
    RefreshToogle<Integer> toogler;

    @Mock Action enableRefresh;
    @Mock Action disableRefresh;

    @Before public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);

        ToogleRefreshView view = new ToogleRefreshView() {

            @Override public Action disableRefresh() {
                return disableRefresh;
            }

            @Override public Action enableRefresh() {
                return enableRefresh;
            }
        };

        toogler = new RefreshToogle<>(view, uiScheduler);
    }

    @Test public void shouldDisableFirstEnableAfter_WhenFlowEmmits() throws Exception {
        Flowable.just(10, 20, 30, 40, 50)
                .compose(toogler)
                .subscribe();

        verifyDisableFirstEnableAfter();
    }

    @Test public void shouldDisableFirstEnableAfter_WhenEmptyFlow() throws Exception {
        Flowable<Integer> empty = Flowable.empty();
        empty.compose(toogler).subscribe();

        verifyDisableFirstEnableAfter();
    }

    @Test public void shouldNotAssignEmpty_WithNotTargetError() throws Exception {
        Flowable<Integer> broken = Flowable.error(new RuntimeException("WTF!!"));

        broken.compose(toogler)
                .subscribe(
                        some -> {},
                        Throwable::printStackTrace,
                        () -> {}
                );

        verifyDisableFirstNotEnableAfter();
    }

    private void verifyDisableFirstEnableAfter() throws Exception {
        InOrder inOrder = inOrder(disableRefresh, enableRefresh);
        inOrder.verify(disableRefresh, oneTimeOnly()).run();
        inOrder.verify(enableRefresh, oneTimeOnly()).run();
    }

    private void verifyDisableFirstNotEnableAfter() throws Exception {
        InOrder inOrder = inOrder(disableRefresh, enableRefresh);
        inOrder.verify(disableRefresh, oneTimeOnly()).run();
        inOrder.verify(enableRefresh, never()).run();
    }
}
