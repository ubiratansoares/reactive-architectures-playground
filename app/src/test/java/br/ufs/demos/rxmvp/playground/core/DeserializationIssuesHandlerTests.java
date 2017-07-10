package br.ufs.demos.rxmvp.playground.core;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import org.junit.Test;

import br.ufs.demos.rxmvp.playground.core.behaviours.errors.UnexpectedResponseError;
import br.ufs.demos.rxmvp.playground.core.infraerrors.DeserializationIssuesHandler;
import io.reactivex.Flowable;

/**
 * Created by bira on 7/10/17.
 */

public class DeserializationIssuesHandlerTests {

    DeserializationIssuesHandler<String> handler = new DeserializationIssuesHandler<>();

    @Test public void shouldHandle_GsonThrowsIllegalStateException() {
        Flowable<String> broken = Flowable.error(new IllegalStateException("Some message"));

        broken.compose(handler)
                .test()
                .assertError(this::checkHandledAsDeserializationError);
    }

    @Test public void shouldHandle_GsonThrowsIOException() {
        Flowable<String> broken = Flowable.error(new JsonIOException("Cannot read file"));

        broken.compose(handler)
                .test()
                .assertError(this::checkHandledAsDeserializationError);
    }

    @Test public void shouldHandle_GsonThrowsSintaxException() {
        Flowable<String> broken =
                Flowable.error(new JsonSyntaxException("Json should not have comments"));

        broken.compose(handler)
                .test()
                .assertError(this::checkHandledAsDeserializationError);
    }

    @Test public void shouldHandle_GsonThrowsParseException() {
        Flowable<String> broken = Flowable.error(new JsonParseException("Failed to parse object"));

        broken.compose(handler)
                .test()
                .assertError(this::checkHandledAsDeserializationError);
    }

    @Test public void shouldNotHandle_OtherErros() {
        IllegalAccessError fuck = new IllegalAccessError("FUCK");
        Flowable<String> broken = Flowable.error(fuck);

        broken.compose(handler)
                .test()
                .assertError(throwable -> throwable.equals(fuck));
    }

    private boolean checkHandledAsDeserializationError(Throwable throwable) {

        if (throwable instanceof UnexpectedResponseError) {
            return throwable.getMessage().contentEquals("Deserialization Error");
        }

        return false;
    }

}
