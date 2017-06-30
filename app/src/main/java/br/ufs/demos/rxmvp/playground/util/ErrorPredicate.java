package br.ufs.demos.rxmvp.playground.util;

/**
 * Created by bira on 6/30/17.
 */

public interface ErrorPredicate {

    boolean evaluate(Throwable error);

}
