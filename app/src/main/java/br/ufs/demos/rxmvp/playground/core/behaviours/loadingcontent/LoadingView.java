package br.ufs.demos.rxmvp.playground.core.behaviours.loadingcontent;

import io.reactivex.functions.Action;

/**
 * Created by bira on 6/29/17.
 */

public interface LoadingView {

    Action showLoading();

    Action hideLoading();

}
