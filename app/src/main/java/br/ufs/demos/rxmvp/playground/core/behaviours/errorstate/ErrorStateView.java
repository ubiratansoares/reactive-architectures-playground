package br.ufs.demos.rxmvp.playground.core.behaviours.errorstate;

import io.reactivex.functions.Action;

/**
 * Created by bira on 6/30/17.
 */

public interface ErrorStateView {

    Action showErrorState();

    Action hideErrorState();

}
