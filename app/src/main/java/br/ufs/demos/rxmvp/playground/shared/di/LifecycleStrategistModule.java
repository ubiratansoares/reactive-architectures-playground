package br.ufs.demos.rxmvp.playground.shared.di;

import android.arch.lifecycle.LifecycleOwner;

import br.ufs.demos.rxmvp.playground.shared.lifecyclestrategy.DisposeStrategy;
import br.ufs.demos.rxmvp.playground.shared.lifecyclestrategy.LifecycleStrategist;
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
