package br.ufs.demos.rxmvp.playground.core;

import android.arch.lifecycle.LifecycleOwner;
import android.support.v4.app.FragmentActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import br.ufs.demos.rxmvp.playground.BuildConfig;
import br.ufs.demos.rxmvp.playground.app.MainApplication;
import br.ufs.demos.rxmvp.playground.core.lifecycles.DisposeStrategy;
import br.ufs.demos.rxmvp.playground.core.lifecycles.LifecycleStrategist;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

import static br.ufs.demos.rxmvp.playground.util.MockitoHelpers.oneTimeOnly;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.robolectric.Robolectric.buildActivity;

/**
 * Created by bira on 10/5/17.
 */

@RunWith(RobolectricTestRunner.class)
@Config(
        constants = BuildConfig.class,
        application = MainApplication.class,
        sdk = 25,
        packageName = "br.ufs.demos.rxmvp.playground"
)
public class LifecycleStrategistTests {

    LifecycleOwner lifecycleOwner;
    DisposeStrategy mockedStrategy;
    LifecycleStrategist strategist;

    @Before public void setup() {
        lifecycleOwner = buildActivity(FragmentActivity.class).create().get();
        mockedStrategy = mock(DisposeStrategy.class);
        strategist = new LifecycleStrategist(lifecycleOwner, mockedStrategy);
    }

    @Test public void shouldApplyStrategy() {
        Disposable target = Flowable.just("Test").subscribe();
        strategist.applyStrategy(target);
        verify(mockedStrategy, oneTimeOnly()).addDisposable(Mockito.any());
    }

}
