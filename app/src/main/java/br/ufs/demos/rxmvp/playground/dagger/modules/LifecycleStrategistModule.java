package br.ufs.demos.rxmvp.playground.dagger.modules;

import android.arch.lifecycle.LifecycleOwner;

import br.ufs.demos.rxmvp.playground.core.lifecycles.DisposeStrategy;
import br.ufs.demos.rxmvp.playground.core.lifecycles.LifecycleStrategist;
import dagger.Module;
import dagger.Provides;

/**
 * Created by bira on 7/5/17.
 */

@Module
public class LifecycleStrategistModule {

    @Provides static LifecycleStrategist strategist(LifecycleOwner owner) {
        return new LifecycleStrategist(owner, new DisposeStrategy());
    }

}
