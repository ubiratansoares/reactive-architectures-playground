package br.ufs.demos.rxmvp.playground.fakes;

import br.ufs.demos.rxmvp.playground.trivia.infrastructure.NumbersTriviaPayload;
import br.ufs.demos.rxmvp.playground.webservice.NumbersWebService;
import io.reactivex.Flowable;

/**
 * Created by bira on 10/3/17.
 */

public class FakeWebService implements NumbersWebService {

    public enum NextCall {
        UNDEFINED_ERROR,
        CONNECTION_ERROR,
        CONNECTION_TIMEOUT,
        INTERNAL_SERVER_ERROR_5xy,
        NOT_FOUND_404,
        SUCCESS_200
    }

    private NextCall nextReturn;


    public FakeWebService() {
        nextReturn = NextCall.UNDEFINED_ERROR;
    }

    public void nextCallPerform(NextCall nextReturn) {
        this.nextReturn = nextReturn;
    }

    @Override public Flowable<NumbersTriviaPayload> getTrivia(String commaSeparatedNumbers) {

        switch (nextReturn) {
            case SUCCESS_200:
                return FakeResponses.triviaResults();

            case NOT_FOUND_404:
                return FakeResponses.notFound();

            case INTERNAL_SERVER_ERROR_5xy:
                return FakeResponses.internalServerError();

            case CONNECTION_ERROR:
                return FakeResponses.connectionIssue();

            case CONNECTION_TIMEOUT:
                return FakeResponses.requestTimeout();
        }

        return Flowable.empty();
    }

}
