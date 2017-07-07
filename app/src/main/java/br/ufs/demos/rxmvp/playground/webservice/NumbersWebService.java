package br.ufs.demos.rxmvp.playground.webservice;

import br.ufs.demos.rxmvp.playground.trivia.infrastructure.NumbersTriviaPayload;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by bira on 6/26/17.
 */

public interface NumbersWebService {

    String BASE_URL = "http://numbersapi.com";

    @GET("/{numbers}/trivia") Flowable<NumbersTriviaPayload> getTrivia(
            @Path("numbers") String commaSeparatedNumbers
    );

}
