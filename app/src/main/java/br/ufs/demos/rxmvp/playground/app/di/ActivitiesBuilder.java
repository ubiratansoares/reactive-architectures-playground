package br.ufs.demos.rxmvp.playground.app.di;

import android.app.Activity;

import br.ufs.demos.rxmvp.playground.triva.FactsAboutNumbersActivity;
import br.ufs.demos.rxmvp.playground.triva.di.TriviaComponent;
import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by bira on 6/26/17.
 */

@Module
public abstract class ActivitiesBuilder {

    @Binds @IntoMap @ActivityKey(FactsAboutNumbersActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindMainActivity(
            TriviaComponent.Builder builder
    );

}
