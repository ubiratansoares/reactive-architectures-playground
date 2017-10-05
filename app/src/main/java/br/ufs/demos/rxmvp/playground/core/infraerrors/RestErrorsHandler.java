package br.ufs.demos.rxmvp.playground.core.infraerrors;

import org.reactivestreams.Publisher;

import br.ufs.demos.rxmvp.playground.core.behaviours.errors.ContentNotFoundError;
import br.ufs.demos.rxmvp.playground.core.behaviours.errors.UnexpectedResponseError;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import retrofit2.HttpException;

/**
 * Created by bira on 6/29/17.
 */

public class RestErrorsHandler<T> implements FlowableTransformer<T, T> {

    @Override public Publisher<T> apply(Flowable<T> upstream) {
        return upstream.onErrorResumeNext(this::handleIfRestError);
    }

    private Publisher<T> handleIfRestError(Throwable throwable) {

        if (otherThanNotFound(throwable)) {
            return Flowable.error(new UnexpectedResponseError("Undesired response for this call"));
        }

        if (notFound(throwable)) {
            return Flowable.error(new ContentNotFoundError());
        }

        return Flowable.error(throwable);
    }

    private boolean otherThanNotFound(Throwable throwable) {
        if (throwable instanceof HttpException) {
            return !notFound(throwable);
        }

        return false;
    }

    private boolean notFound(Throwable throwable) {

        if (throwable instanceof HttpException) {
            HttpException error = (HttpException) throwable;
            return error.code() == 404;
        }

        return false;
    }
}
