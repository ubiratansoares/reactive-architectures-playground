package br.ufs.demos.rxmvp.playground.trivia.infrastructure;

import java.util.List;

import br.ufs.demos.rxmvp.playground.core.infraerrors.DeserializationIssuesHandler;
import br.ufs.demos.rxmvp.playground.core.infraerrors.RestErrorsHandler;
import br.ufs.demos.rxmvp.playground.webservice.NumbersWebService;
import br.ufs.demos.rxmvp.playground.core.behaviours.networking.NetworkingErrorHandler;
import br.ufs.demos.rxmvp.playground.trivia.domain.FactAboutNumber;
import br.ufs.demos.rxmvp.playground.trivia.domain.GetRandomFacts;
import br.ufs.demos.rxmvp.playground.trivia.domain.TriviaGenerator;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;

/**
 * Created by bira on 6/27/17.
 */

public class TriviaInfrastructure implements GetRandomFacts {

    private NumbersWebService webService;
    private TriviaGenerator triviaGenerator;
    private PayloadMapper mapper;
    private PayloadValidator validator;
    private Scheduler executionScheduler;

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
                .compose(new NetworkingErrorHandler<>())
                .compose(new RestErrorsHandler<>())
                .compose(new DeserializationIssuesHandler<>())
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
