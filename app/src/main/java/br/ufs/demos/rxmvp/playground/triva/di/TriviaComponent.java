package br.ufs.demos.rxmvp.playground.triva.di;

import br.ufs.demos.rxmvp.playground.triva.FactsAboutNumbersActivity;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by bira on 6/26/17.
 */

@Subcomponent(modules = TriviaModule.class)
public interface TriviaComponent extends AndroidInjector<FactsAboutNumbersActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<FactsAboutNumbersActivity> {
        // Hook to main activity
    }

}
