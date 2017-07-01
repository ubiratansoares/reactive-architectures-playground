package br.ufs.demos.rxmvp.playground.util;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

import static br.ufs.demos.rxmvp.playground.util.MockitoHelpers.onlyOnce;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Created by bira on 6/30/17.
 */

public class DataFlowWatcher {

    private Consumer<?> onNext;
    private Consumer<Throwable> onError;
    private Action onCompleted;

    private DataFlowWatcher(Consumer<?> onNext,
                            Consumer<Throwable> onError,
                            Action onCompleted) {

        this.onNext = onNext;
        this.onError = onError;
        this.onCompleted = onCompleted;
    }

    public static DataFlowWatcher with(Consumer<?> onNext,
                                       Consumer<Throwable> onError,
                                       Action onCompleted) {

        return new DataFlowWatcher(onNext, onError, onCompleted);
    }

    public void shouldReceiveItems(int quantity) throws Exception {

        if (quantity == 0) {
            throw new IllegalArgumentException("Should receive at least one item");
        }

        verify(onNext, atMost(quantity)).accept(any());
        verify(onCompleted).run();
        verifyZeroInteractions(onError);
    }

    public void shouldFinishWithError() throws Exception {
        verify(onError, onlyOnce()).accept(any());
        verifyZeroInteractions(onNext);
        verifyZeroInteractions(onError);
    }

    public void shouldNotReceiveAnyData() throws Exception {
        verify(onCompleted, onlyOnce()).run();
        verifyZeroInteractions(onNext);
        verifyZeroInteractions(onError);
    }
}
