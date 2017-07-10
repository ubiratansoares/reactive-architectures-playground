package br.ufs.demos.rxmvp.playground.core.lifecycles;

import android.arch.lifecycle.LifecycleOwner;

import io.reactivex.disposables.Disposable;

/**
 * Created by bira on 6/30/17.
 */

public class LifecycleStrategist {

    private DisposeStrategy strategy;

    public LifecycleStrategist(LifecycleOwner owner, DisposeStrategy strategy) {
        this.strategy = strategy;
        owner.getLifecycle().addObserver(strategy);
    }

    public void applyStrategy(Disposable toDispose) {
        strategy.addDisposable(toDispose);
    }

}
