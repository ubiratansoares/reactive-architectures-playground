package br.ufs.demos.rxmvp.playground.dagger.modules;

import br.ufs.demos.rxmvp.playground.trivia.ui.FactsAboutNumbersActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by bira on 6/26/17.
 */

@Module
public abstract class ActivitiesBuilder {

    @ContributesAndroidInjector(modules = FactsAboutNumbersModule.class)
    abstract FactsAboutNumbersActivity factsAboutNumbersActivity();

}
