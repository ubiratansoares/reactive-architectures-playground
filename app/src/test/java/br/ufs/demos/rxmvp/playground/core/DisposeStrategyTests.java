package br.ufs.demos.rxmvp.playground.core;

import org.junit.Test;

import br.ufs.demos.rxmvp.playground.core.lifecycles.DisposeStrategy;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by bira on 10/5/17.
 */

public class DisposeStrategyTests {

    @Test public void shouldDispose_OnDestroy() {
        DisposeStrategy strategy = new DisposeStrategy();
        Disposable target = Flowable.just("A", "B", "C").subscribe();
        strategy.addDisposable(target);
        strategy.onDestroy();
        assertThat(target.isDisposed()).isTrue();

    }

}
