package br.ufs.demos.rxmvp.playground.core.behaviours.errors;

/**
 * Created by bira on 6/29/17.
 */

public class UnexpectedResponseError extends RuntimeException {

    public UnexpectedResponseError(String message) {
        super(message);
    }

}
