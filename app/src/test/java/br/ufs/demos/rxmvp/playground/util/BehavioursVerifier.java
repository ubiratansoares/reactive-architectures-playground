package br.ufs.demos.rxmvp.playground.util;

import org.mockito.InOrder;

import br.ufs.demos.rxmvp.playground.shared.emptystate.EmptyStateView;
import br.ufs.demos.rxmvp.playground.shared.errorstate.ErrorStateView;
import br.ufs.demos.rxmvp.playground.shared.loadingcontent.LoadingView;

import static br.ufs.demos.rxmvp.playground.util.MockitoHelpers.oneTimeOnly;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by bira on 6/30/17.
 */

public class BehavioursVerifier {

    private Object target;

    private BehavioursVerifier(Object target) {
        this.target = target;
    }

    public static BehavioursVerifier with(Object target) {
        return new BehavioursVerifier(target);
    }

    public BehavioursVerifier shouldShowErrorState() throws Exception {
        checkErrorStateView();
        ErrorStateView view = (ErrorStateView) target;
        verify(view.showErrorState(), oneTimeOnly()).run();
        return this;
    }

    public BehavioursVerifier shouldNotShowErrorState() throws Exception {
        checkErrorStateView();
        ErrorStateView view = (ErrorStateView) target;
        verify(view.showErrorState(), never()).run();
        return this;
    }

    public BehavioursVerifier shouldShowEmptyState() throws Exception {
        checkEmptyStateView();
        EmptyStateView view = (EmptyStateView) target;
        verify(view.showEmptyState(), oneTimeOnly()).run();
        return this;
    }

    public BehavioursVerifier shouldNotShowEmptyState() throws Exception {
        checkEmptyStateView();
        EmptyStateView view = (EmptyStateView) target;
        verify(view.showEmptyState(), never()).run();
        return this;
    }

    public BehavioursVerifier showLoadingFirstHideLoadingAfter() throws Exception {
        checkLoadingView();
        LoadingView view = (LoadingView) target;

        InOrder inOrder = inOrder(view.showLoading(), view.hideLoading());
        inOrder.verify(view.showLoading(), oneTimeOnly()).run();
        inOrder.verify(view.hideLoading(), oneTimeOnly()).run();

        return this;
    }

    private void checkEmptyStateView() {
        if (!(target instanceof EmptyStateView))
            throw new IllegalArgumentException("Target view not instance of EmptyStateView");

    }

    private void checkErrorStateView() {
        if (!(target instanceof ErrorStateView))
            throw new IllegalArgumentException("Target view not instance of ErrorStateView");

    }

    private void checkLoadingView() {
        if (!(target instanceof LoadingView))
            throw new IllegalArgumentException("Target view not instance of LoadingView");

    }
}