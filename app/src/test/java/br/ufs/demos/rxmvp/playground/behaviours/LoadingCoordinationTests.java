package br.ufs.demos.rxmvp.playground.behaviours;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.ufs.demos.rxmvp.playground.shared.loadingcontent.LoadingCoordination;
import br.ufs.demos.rxmvp.playground.shared.loadingcontent.LoadingView;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

import static br.ufs.demos.rxmvp.playground.util.MockitoHelpers.onlyOnce;
import static org.mockito.Mockito.verify;

/**
 * Created by bira on 6/30/17.
 */

public class LoadingCoordinationTests {

    Scheduler uiScheduler = Schedulers.trampoline();
    LoadingCoordination<String> loadingCoordination;

    @Mock Action show;
    @Mock Action hide;
    
    @Before public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);

        LoadingView view = new LoadingView() {
            @Override public Action showLoading() {return show;}
            @Override public Action hideLoading() {return hide;}
        };

        loadingCoordination = new LoadingCoordination<>(view, uiScheduler);
    }

    @Test public void shouldNotAssignEmpty_WhenFlowEmmits() throws Exception {
        Flowable.just("A", "B", "C")
                .compose(loadingCoordination)
                .subscribe();

        checkLoadingCoordinated();
    }


    @Test public void shouldNotAssignError_WithEmptyFlow() throws Exception {
        Flowable<String> empty = Flowable.empty();
        empty.compose(loadingCoordination).subscribe();

        checkLoadingCoordinated();
    }

    @Test public void shouldAssignError_WithErrorAtFlow() throws Exception {
        Flowable<String> broken = Flowable.error(new RuntimeException("WTF!!"));

        broken.compose(loadingCoordination)
                .subscribe(
                        some -> {},
                        Throwable::printStackTrace,
                        () -> {}
                );

        checkLoadingCoordinated();
    }

    private void checkLoadingCoordinated() throws Exception {
        verify(hide, onlyOnce()).run();
        verify(show, onlyOnce()).run();
    }

}
