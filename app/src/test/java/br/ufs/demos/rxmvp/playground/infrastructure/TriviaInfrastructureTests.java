package br.ufs.demos.rxmvp.playground.infrastructure;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import br.ufs.demos.rxmvp.playground.webservice.NumbersWebService;
import br.ufs.demos.rxmvp.playground.trivia.domain.TriviaGenerator;
import br.ufs.demos.rxmvp.playground.trivia.domain.errors.ContentNotFoundError;
import br.ufs.demos.rxmvp.playground.trivia.domain.errors.UnexpectedResponseError;
import br.ufs.demos.rxmvp.playground.trivia.infrastructure.PayloadMapper;
import br.ufs.demos.rxmvp.playground.trivia.infrastructure.PayloadValidator;
import br.ufs.demos.rxmvp.playground.trivia.infrastructure.TriviaInfrastructure;
import io.reactivex.schedulers.Schedulers;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static br.ufs.demos.rxmvp.playground.util.FilesFromTestResources.readFile;


/**
 * Created by bira on 6/27/17.
 */

public class TriviaInfrastructureTests {

    TriviaInfrastructure infrastructure;
    MockWebServer server;

    @Before public void beforeEachTest() {

        server = new MockWebServer();

        NumbersWebService numberAPI =
                new Retrofit.Builder()
                        .baseUrl(server.url("/").toString())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
                        .create(NumbersWebService.class);

        infrastructure = new TriviaInfrastructure(
                numberAPI,
                new TriviaGenerator(),
                new PayloadMapper(),
                new PayloadValidator(),
                Schedulers.trampoline() // non-concurrent integration on tests
        );
    }

    @After public void afterEachTest() throws IOException {
        server.shutdown();
    }

    @Test public void checkIntegration_200OK_CorrectPayload() {

        String json = readFile("sample_response_200OK.json");

        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(json)
        );

        infrastructure.fetchTrivia()
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValueCount(6); // Check at payload file
    }

    @Test public void checkIntegration_200OK_EmptyPayload() {

        String json = readFile("response_empty_200OK.json");

        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(json)
        );

        infrastructure.fetchTrivia()
                .test()
                .assertNoErrors()
                .assertNoValues()
                .assertComplete();
    }

    @Test public void checkIntegration_404Error_AsNotFound() {
        server.enqueue(new MockResponse().setResponseCode(404));

        infrastructure.fetchTrivia()
                .test()
                .assertNoValues()
                .assertError(ContentNotFoundError.class);
    }

    @Test public void checkIntegration_200OK_BrokenPayload_AsUnexpected() {

        String json = readFile("response_broken_200OK.json");

        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(json)
        );

        infrastructure.fetchTrivia()
                .test()
                .assertNoValues()
                .assertError(UnexpectedResponseError.class);
    }

    @Test public void checkIntegration_5xxError_AsUnexpected() {
        server.enqueue(new MockResponse().setResponseCode(502));

        infrastructure.fetchTrivia()
                .test()
                .assertNoValues()
                .assertError(UnexpectedResponseError.class);
    }

    @Test public void checkIntegration_400Error_AsUnexpected() {
        server.enqueue(new MockResponse().setResponseCode(400));

        infrastructure.fetchTrivia()
                .test()
                .assertNoValues()
                .assertError(UnexpectedResponseError.class);
    }

}
