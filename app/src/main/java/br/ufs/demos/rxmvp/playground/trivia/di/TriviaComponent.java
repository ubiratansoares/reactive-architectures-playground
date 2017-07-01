package br.ufs.demos.rxmvp.playground.trivia.di;

import br.ufs.demos.rxmvp.playground.trivia.FactsAboutNumbersActivity;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by bira on 6/26/17.
 */

@Subcomponent(
        modules = PresentationModule.class)
public interface TriviaComponent extends AndroidInjector<FactsAboutNumbersActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<FactsAboutNumbersActivity> {
        // Hook to main activity
    }

}
