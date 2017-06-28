package br.ufs.demos.rxmvp.playground.triva.infrastructure;

import java.util.List;

import br.ufs.demos.rxmvp.playground.triva.domain.FactAboutNumber;
import br.ufs.demos.rxmvp.playground.triva.domain.TriviaForNumbersSource;
import br.ufs.demos.rxmvp.playground.triva.domain.TriviaGenerator;
import br.ufs.demos.rxmvp.playground.networking.RestWebService;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bira on 6/27/17.
 */

public class TriviaInfrastructure implements TriviaForNumbersSource {

    RestWebService webService;
    TriviaGenerator generator;
    PayloadMapper mapper;
    PayloadValidator validator;

    public TriviaInfrastructure(RestWebService webService,
                                TriviaGenerator generator,
                                PayloadMapper mapper,
                                PayloadValidator validator) {
        this.webService = webService;
        this.generator = generator;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Override public Flowable<FactAboutNumber> fetchTrivia() {

        List<Integer> numbersForTrivia = generator.numberForTrivia();
        String formattedUrlPath = formatPathWithCommas(numbersForTrivia);

        return webService
                .getTrivia(formattedUrlPath)
                .subscribeOn(Schedulers.io())
                .filter(payload -> validator.validate(payload))
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
