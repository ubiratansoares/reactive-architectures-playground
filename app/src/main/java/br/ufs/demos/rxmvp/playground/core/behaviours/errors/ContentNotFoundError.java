package br.ufs.demos.rxmvp.playground.core.behaviours.errors;

/**
 * Created by bira on 6/29/17.
 */

public class ContentNotFoundError extends RuntimeException {

    @Override public String getMessage() {
        return "No content available";
    }

}
