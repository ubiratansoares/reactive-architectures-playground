package br.ufs.demos.rxmvp.playground.shared;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by bira on 6/30/17.
 */

public class DisposeStrategy implements LifecycleObserver {

    private CompositeDisposable composite = new CompositeDisposable();

    void addDisposable(Disposable toDispose) {
        composite.add(toDispose);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY) public void onDestroy() {
        composite.dispose();
    }

}
