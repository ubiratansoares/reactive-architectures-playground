package br.ufs.demos.rxmvp.playground.trivia.infrastructure;

import java.util.List;

import br.ufs.demos.rxmvp.playground.networking.NumbersWebService;
import br.ufs.demos.rxmvp.playground.trivia.domain.FactAboutNumber;
import br.ufs.demos.rxmvp.playground.trivia.domain.TriviaForNumbersSource;
import br.ufs.demos.rxmvp.playground.trivia.domain.TriviaGenerator;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;

/**
 * Created by bira on 6/27/17.
 */

public class TriviaInfrastructure implements TriviaForNumbersSource {

    NumbersWebService webService;
    TriviaGenerator triviaGenerator;
    PayloadMapper mapper;
    PayloadValidator validator;
    Scheduler executionScheduler;

    public TriviaInfrastructure(NumbersWebService webService,
                                TriviaGenerator triviaGenerator,
                                PayloadMapper mapper,
                                PayloadValidator validator,
                                Scheduler executionScheduler) {

        this.webService = webService;
        this.triviaGenerator = triviaGenerator;
        this.mapper = mapper;
        this.validator = validator;
        this.executionScheduler = executionScheduler;
    }

    @Override public Flowable<FactAboutNumber> fetchTrivia() {

        List<Integer> numbersForTrivia = triviaGenerator.numberForTrivia();
        String formattedUrlPath = formatPathWithCommas(numbersForTrivia);

        return webService
                .getTrivia(formattedUrlPath)
                .subscribeOn(executionScheduler)
                .compose(new RestErrorsHandler<>())
                .compose(new DeserializationErrorsHandler<>())
                .filter(payload -> validator.accept(payload))
                .map(payload -> mapper.toNumberFacts(payload))
                .flatMap(Flowable::fromIterable);
    }

    private String formatPathWithCommas(List<Integer> numbersForTrivia) {
        return numbersForTrivia
                .toString()
                .replace(" ", "")
                .replace("[", "")
                .replace("]", "");
    }

}
