package br.ufs.demos.rxmvp.playground.triva.di;

import br.ufs.demos.rxmvp.playground.triva.FactsAboutNumbersActivity;
import dagger.Module;
import dagger.Provides;

/**
 * Created by bira on 6/26/17.
 */

@Module
public class TriviaModule {

    @Provides String provide(FactsAboutNumbersActivity factsAboutNumbersActivity) {
        return "To Inject";
    }

}
