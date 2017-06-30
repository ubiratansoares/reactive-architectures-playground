package br.ufs.demos.rxmvp.playground.shared;

import android.arch.lifecycle.LifecycleOwner;

import io.reactivex.disposables.Disposable;

/**
 * Created by bira on 6/30/17.
 */

public class LifecycleStrategist {

    private DisposeStrategy observer;

    public LifecycleStrategist(LifecycleOwner owner, DisposeStrategy observer) {
        this.observer = observer;
        owner.getLifecycle().addObserver(observer);
    }

    public void applyStrategy(Disposable toDispose) {
        observer.addDisposable(toDispose);
    }

}
