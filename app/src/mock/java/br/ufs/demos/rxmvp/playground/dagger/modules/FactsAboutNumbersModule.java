package br.ufs.demos.rxmvp.playground.dagger.modules;

import android.arch.lifecycle.LifecycleOwner;

import br.ufs.demos.rxmvp.playground.core.behaviours.emptystate.EmptyStateView;
import br.ufs.demos.rxmvp.playground.core.behaviours.errorstate.ErrorStateView;
import br.ufs.demos.rxmvp.playground.core.behaviours.loadingcontent.LoadingView;
import br.ufs.demos.rxmvp.playground.core.behaviours.networking.NetworkingErrorView;
import br.ufs.demos.rxmvp.playground.core.behaviours.tooglerefresh.ToogleRefreshView;
import br.ufs.demos.rxmvp.playground.trivia.presentation.DisplayFactsView;
import br.ufs.demos.rxmvp.playground.trivia.ui.FactsAboutNumbersActivity;
import dagger.Module;
import dagger.Provides;

/**
 * Created by bira on 7/5/17.
 */

@Module(
        includes = {
                BehavioursModule.class,
                LifecycleStrategistModule.class,
                PresentationModule.class
        }
)
public class FactsAboutNumbersModule {

    @Provides static DisplayFactsView displayFactsView(FactsAboutNumbersActivity activity) {
        return activity;
    }

    @Provides static ToogleRefreshView toogleRefreshView(FactsAboutNumbersActivity activity) {
        return activity;
    }

    @Provides static LoadingView loadingView(FactsAboutNumbersActivity activity) {
        return activity;
    }

    @Provides static ErrorStateView errorStateView(FactsAboutNumbersActivity activity) {
        return activity;
    }

    @Provides static EmptyStateView emptyStateView(FactsAboutNumbersActivity activity) {
        return activity;
    }

    @Provides static NetworkingErrorView networkingErrorView(FactsAboutNumbersActivity activity) {
        return activity;
    }

    @Provides static LifecycleOwner strategist(FactsAboutNumbersActivity activity) {
        return activity;
    }

}
