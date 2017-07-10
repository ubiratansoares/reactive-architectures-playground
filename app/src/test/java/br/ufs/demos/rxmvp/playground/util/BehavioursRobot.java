package br.ufs.demos.rxmvp.playground.util;

import org.mockito.InOrder;

import br.ufs.demos.rxmvp.playground.core.behaviours.emptystate.EmptyStateView;
import br.ufs.demos.rxmvp.playground.core.behaviours.errorstate.ErrorStateView;
import br.ufs.demos.rxmvp.playground.core.behaviours.loadingcontent.LoadingView;
import br.ufs.demos.rxmvp.playground.core.behaviours.networking.NetworkingErrorView;
import br.ufs.demos.rxmvp.playground.core.behaviours.tooglerefresh.ToogleRefreshView;

import static br.ufs.demos.rxmvp.playground.util.MockitoHelpers.oneTimeOnly;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by bira on 6/30/17.
 */

public class BehavioursRobot {

    private Object target;

    private BehavioursRobot(Object target) {
        this.target = target;
    }

    public static BehavioursRobot with(Object target) {
        return new BehavioursRobot(target);
    }

    public BehavioursRobot shouldShowErrorState() throws Exception {
        checkErrorStateView();
        ErrorStateView view = (ErrorStateView) target;
        verify(view.showErrorState(), oneTimeOnly()).run();
        return this;
    }

    public BehavioursRobot shouldNotShowErrorState() throws Exception {
        checkErrorStateView();
        ErrorStateView view = (ErrorStateView) target;
        verify(view.showErrorState(), never()).run();
        return this;
    }

    public BehavioursRobot shouldShowEmptyState() throws Exception {
        checkEmptyStateView();
        EmptyStateView view = (EmptyStateView) target;
        verify(view.showEmptyState(), oneTimeOnly()).run();
        return this;
    }

    public BehavioursRobot shouldNotShowEmptyState() throws Exception {
        checkEmptyStateView();
        EmptyStateView view = (EmptyStateView) target;
        verify(view.showEmptyState(), never()).run();
        return this;
    }

    public BehavioursRobot shouldReportNetworkingError() throws Exception {
        checkNetworkingErrorView();
        NetworkingErrorView view = (NetworkingErrorView) target;
        verify(view.reportNetworkingError(), oneTimeOnly()).run();
        return this;
    }

    public BehavioursRobot shouldNotReportNetworkingError() throws Exception {
        checkNetworkingErrorView();
        NetworkingErrorView view = (NetworkingErrorView) target;
        verify(view.reportNetworkingError(), never()).run();
        return this;
    }

    public BehavioursRobot showLoadingFirstHideLoadingAfter() throws Exception {
        checkLoadingView();
        LoadingView view = (LoadingView) target;
        verifyInOrderShowAndHideLoading(view);
        return this;
    }

    public BehavioursRobot disableRefreshFirstAndEnableAfter() throws Exception {
        checkToogleRefreshView();
        ToogleRefreshView view = (ToogleRefreshView) target;
        verifyDisableEnableRefresh(view);
        return this;
    }

    public BehavioursRobot disableRefreshFirstAndNotEnableAfter() throws Exception {
        checkToogleRefreshView();
        ToogleRefreshView view = (ToogleRefreshView) target;
        verifyDisableAndNotEnableRefresh(view);
        return this;
    }

    private void verifyInOrderShowAndHideLoading(LoadingView view) throws Exception {
        InOrder inOrder = inOrder(view.showLoading(), view.hideLoading());
        inOrder.verify(view.showLoading(), oneTimeOnly()).run();
        inOrder.verify(view.hideLoading(), oneTimeOnly()).run();
    }

    private void verifyDisableEnableRefresh(ToogleRefreshView view) throws Exception {
        InOrder inOrder = inOrder(view.enableRefresh(), view.disableRefresh());
        inOrder.verify(view.disableRefresh(), oneTimeOnly()).run();
        inOrder.verify(view.enableRefresh(), oneTimeOnly()).run();
    }

    private void verifyDisableAndNotEnableRefresh(ToogleRefreshView view) throws Exception {
        InOrder inOrder = inOrder(view.enableRefresh(), view.disableRefresh());
        inOrder.verify(view.disableRefresh(), oneTimeOnly()).run();
        inOrder.verify(view.enableRefresh(), never()).run();
    }

    private void checkEmptyStateView() {
        if (!(target instanceof EmptyStateView))
            throw new IllegalStateException("Target view not instance of EmptyStateView");
    }

    private void checkErrorStateView() {
        if (!(target instanceof ErrorStateView))
            throw new IllegalStateException("Target view not instance of ErrorStateView");
    }

    private void checkLoadingView() {
        if (!(target instanceof LoadingView))
            throw new IllegalStateException("Target view not instance of LoadingView");
    }

    private void checkNetworkingErrorView() {
        if (!(target instanceof NetworkingErrorView))
            throw new IllegalStateException("Target view not instance of NetworkingErroView");
    }

    private void checkToogleRefreshView() {
        if (!(target instanceof ToogleRefreshView))
            throw new IllegalStateException("Target view not instance of ToogleRefreshView");
    }


}
