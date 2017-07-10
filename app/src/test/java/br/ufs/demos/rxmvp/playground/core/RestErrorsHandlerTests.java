package br.ufs.demos.rxmvp.playground.core;

import org.junit.Test;

import br.ufs.demos.rxmvp.playground.core.behaviours.errors.ContentNotFoundError;
import br.ufs.demos.rxmvp.playground.core.behaviours.errors.UnexpectedResponseError;
import br.ufs.demos.rxmvp.playground.core.infraerrors.RestErrorsHandler;
import io.reactivex.Flowable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * Created by bira on 7/10/17.
 */

public class RestErrorsHandlerTests {

    RestErrorsHandler<String> handler = new RestErrorsHandler<>();

    @Test public void shouldHandle_404() {
        ResponseBody body = ResponseBody.create(
                MediaType.parse("application/json"),
                "{\"message\":\"Not found\"}");

        Response<?> error = Response.error(404, body);
        HttpException exp = new HttpException(error);

        Flowable<String> notFound = Flowable.error(exp);

        notFound.compose(handler)
                .test()
                .assertError(ContentNotFoundError.class);
    }

    @Test public void shouldHandle_5xx() {
        ResponseBody body = ResponseBody.create(
                MediaType.parse("application/json"),
                "{\"message\":\"Internal server error\"}");

        Response<?> error = Response.error(503, body);
        HttpException exp = new HttpException(error);

        Flowable<String> internalServer = Flowable.error(exp);

        internalServer.compose(handler)
                .test()
                .assertError(UnexpectedResponseError.class);
    }

    @Test public void shouldNotHandle_OtherErrors() {

        NullPointerException npe = new NullPointerException();
        Flowable<String> otherIssue = Flowable.error(npe);

        otherIssue.compose(handler)
                .test()
                .assertError(throwable -> throwable.equals(npe));
    }
}
