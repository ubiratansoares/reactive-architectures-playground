package br.ufs.demos.rxmvp.playground.trivia.domain;

import io.reactivex.Flowable;

/**
 * Created by bira on 6/26/17.
 */

public interface GetRandomFacts {

    Flowable<FactAboutNumber> fetchTrivia();

}
