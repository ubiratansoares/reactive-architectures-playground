package br.ufs.demos.rxmvp.playground.fakes;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import br.ufs.demos.rxmvp.playground.trivia.infrastructure.NumbersTriviaPayload;
import io.reactivex.Flowable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * Created by bira on 10/3/17.
 */

public class FakeResponses {

    private static final Gson GSON = new Gson();

    static Flowable<NumbersTriviaPayload> triviaResults() {
        return Flowable.just(fakeTrivia());
    }

    static Flowable<NumbersTriviaPayload> internalServerError() {
        Response<?> response = Response.error(503, bodyFor(internalServerErrorMessage()));
        HttpException exp = new HttpException(response);
        Flowable<NumbersTriviaPayload> error = Flowable.error(exp);
        return error.delay(1, TimeUnit.SECONDS);
    }

    static Flowable<NumbersTriviaPayload> notFound() {
        Response<?> response = Response.error(404, bodyFor(notFoundMessage()));
        HttpException exp = new HttpException(response);
        return Flowable.error(exp);
    }

    static Flowable<NumbersTriviaPayload> connectionIssue() {
        return Flowable.error(new IOException("Canceled"));
    }

    static Flowable<NumbersTriviaPayload> requestTimeout() {
        return Flowable.error(new SocketTimeoutException("Read timeout"));
    }

    private static ResponseBody bodyFor(String error) {
        return ResponseBody.create(MediaType.parse("application/json"), error);
    }

    private static NumbersTriviaPayload fakeTrivia() {
        String json = "{\n" +
                "  \"1\": \"1 is the number of moons orbiting Earth.\",\n" +
                "  \"10\": \"10 is the Number of dots in a tetractys.\",\n" +
                "  \"42\": \"42 is the number of US gallons in a barrel of oil.\",\n" +
                "  \"567\": \"567 is an unremarkable number.\",\n" +
                "  \"1000\": \"1000 is the number of origami cranes to fold to be granted a wish by a crane, according to an ancient Japanese legend.\",\n" +
                "  \"1000000\": \"1000000 is the number of colors that can be distinguished by the trichromatic color vision of the human eye.\"\n" +
                "}";

        return GSON.fromJson(json, NumbersTriviaPayload.class);
    }

    private static String internalServerErrorMessage() {
        return "  \"Error\": \"Out of capacity.\",\n";
    }

    private static String notFoundMessage() {
        return "  \"Error\": \"Not Found.\",\n";
    }

}
