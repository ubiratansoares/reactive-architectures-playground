package br.ufs.demos.rxmvp.playground.dagger.modules;

import br.ufs.demos.rxmvp.playground.dagger.qualifiers.IOScheduler;
import br.ufs.demos.rxmvp.playground.trivia.domain.GetRandomFacts;
import br.ufs.demos.rxmvp.playground.trivia.domain.TriviaGenerator;
import br.ufs.demos.rxmvp.playground.trivia.infrastructure.PayloadMapper;
import br.ufs.demos.rxmvp.playground.trivia.infrastructure.PayloadValidator;
import br.ufs.demos.rxmvp.playground.trivia.infrastructure.TriviaInfrastructure;
import br.ufs.demos.rxmvp.playground.webservice.NumbersWebService;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

/**
 * Created by bira on 7/1/17.
 */

@Module
public class InfrastructureModule {

    @Provides static GetRandomFacts infrastructure(
            NumbersWebService webService,
            TriviaGenerator generator,
            PayloadMapper mapper,
            PayloadValidator validator,
            @IOScheduler Scheduler ioScheduler) {

        return new TriviaInfrastructure(webService, generator, mapper, validator, ioScheduler);
    }

    @Provides static PayloadMapper mapper() {
        return new PayloadMapper();
    }

    @Provides static PayloadValidator validator() {
        return new PayloadValidator();
    }

    @Provides static TriviaGenerator generator() {
        return new TriviaGenerator();
    }
}
