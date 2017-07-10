package br.ufs.demos.rxmvp.playground.core.behaviours.emptystate;

import io.reactivex.functions.Action;

/**
 * Created by bira on 6/30/17.
 */

public interface EmptyStateView {

    Action showEmptyState();

    Action hideEmptyState();

}
