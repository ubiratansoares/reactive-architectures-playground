package br.ufs.demos.rxmvp.playground.dagger.modules;

import android.arch.lifecycle.LifecycleOwner;

import br.ufs.demos.rxmvp.playground.core.behaviours.emptystate.EmptyStateView;
import br.ufs.demos.rxmvp.playground.core.behaviours.errorstate.ErrorStateView;
import br.ufs.demos.rxmvp.playground.core.behaviours.loadingcontent.LoadingView;
import br.ufs.demos.rxmvp.playground.core.behaviours.networking.NetworkingErrorView;
import br.ufs.demos.rxmvp.playground.core.behaviours.tooglerefresh.ToogleRefreshView;
import br.ufs.demos.rxmvp.playground.trivia.presentation.DisplayFactsView;
import br.ufs.demos.rxmvp.playground.trivia.ui.FactsAboutNumbersActivity;
import dagger.Binds;
import dagger.Module;

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
public abstract class FactsAboutNumbersModule {

    @Binds abstract DisplayFactsView displayFactsView(FactsAboutNumbersActivity activity);

    @Binds abstract ToogleRefreshView toogleRefreshView(FactsAboutNumbersActivity activity);

    @Binds abstract LoadingView loadingView(FactsAboutNumbersActivity activity);

    @Binds abstract ErrorStateView errorStateView(FactsAboutNumbersActivity activity);

    @Binds abstract EmptyStateView emptyStateView(FactsAboutNumbersActivity activity);

    @Binds abstract NetworkingErrorView networkingErrorView(FactsAboutNumbersActivity activity);

    @Binds abstract LifecycleOwner strategist(FactsAboutNumbersActivity activity);

}
