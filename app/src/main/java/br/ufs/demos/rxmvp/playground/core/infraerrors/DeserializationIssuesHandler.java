package br.ufs.demos.rxmvp.playground.core.infraerrors;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import org.reactivestreams.Publisher;

import br.ufs.demos.rxmvp.playground.core.behaviours.errors.UnexpectedResponseError;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

/**
 * Created by bira on 6/29/17.
 *
 * Handles any errors throwed by GSON and report an UnexpectedResponse to
 * next steps of pipeline
 *
 */

public class DeserializationIssuesHandler<T> implements FlowableTransformer<T, T> {

    @Override public Publisher<T> apply(Flowable<T> upstream) {
        return upstream.onErrorResumeNext(this::handleErrorFromDeserializer);
    }

    private Publisher<T> handleErrorFromDeserializer(Throwable throwable) {

        if (isDeserializationError(throwable)) {
            return Flowable.error(new UnexpectedResponseError("Deserialization Error"));
        }

        return Flowable.error(throwable);
    }

    private boolean isDeserializationError(Throwable throwable) {
        return throwable instanceof IllegalStateException
                || throwable instanceof JsonIOException
                || throwable instanceof JsonSyntaxException
                || throwable instanceof JsonParseException;
    }
}
