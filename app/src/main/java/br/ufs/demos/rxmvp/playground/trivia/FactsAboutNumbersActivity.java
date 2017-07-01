package br.ufs.demos.rxmvp.playground.trivia;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import javax.inject.Inject;

import br.ufs.demos.rxmvp.playground.R;
import br.ufs.demos.rxmvp.playground.trivia.presentation.DisplayFactsView;
import br.ufs.demos.rxmvp.playground.trivia.presentation.FactViewModel;
import br.ufs.demos.rxmvp.playground.trivia.presentation.FactsPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

public class FactsAboutNumbersActivity extends AppCompatActivity
        implements DisplayFactsView, LifecycleRegistryOwner {

    @BindView(R.id.progressBar) ProgressBar loading;
    @Inject FactsPresenter presenter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override protected void onResume() {
        super.onResume();
        presenter.fetchRandomFacts();
    }

    @Override public LifecycleRegistry getLifecycle() {
        return new LifecycleRegistry(this);
    }

    @Override public Action showLoading() {
        return () -> loading.setVisibility(View.VISIBLE);
    }

    @Override public Action hideLoading() {
        return () -> loading.setVisibility(View.GONE);
    }

    @Override public Action showEmptyState() {
        return () -> Log.i("Screen", "showEmptyState");
    }

    @Override public Action hideEmptyState() {
        return () -> Log.i("Screen", "hideEmptyState");
    }

    @Override public Action showErrorState() {
        return () -> Log.i("Screen", "showErrorState");
    }

    @Override public Action hideErrorState() {
        return () -> Log.i("Screen", "hideErrorState");
    }

    @Override public Disposable subscribeInto(Flowable<FactViewModel> flow) {
        return flow.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        model -> Log.v("Screen", "next = " + model.toString()),
                        throwable -> Log.e("Screen", "Error -> " + throwable.getMessage()),
                        () -> Log.i("Screen", "Done")
                );
    }

}
