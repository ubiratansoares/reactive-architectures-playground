package br.ufs.demos.rxmvp.playground.trivia.ui;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

import br.ufs.demos.rxmvp.playground.R;
import br.ufs.demos.rxmvp.playground.trivia.presentation.DisplayFactsView;
import br.ufs.demos.rxmvp.playground.trivia.presentation.FactsPresenter;
import br.ufs.demos.rxmvp.playground.trivia.presentation.models.FactViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

public class FactsAboutNumbersActivity
        extends AppCompatActivity implements DisplayFactsView, LifecycleRegistryOwner {

    private static final String TAG = FactsAboutNumbersActivity.class.getSimpleName();

    @BindView(R.id.container) View container;
    @BindView(R.id.label_feedback_message) TextView feedbackMessage;
    @BindView(R.id.progressBar) ProgressBar loading;
    @BindView(R.id.recyclerview_facts) RecyclerView factsView;
    @BindView(R.id.fab) FloatingActionButton fab;

    @Inject FactsPresenter presenter;

    public FactsAdapter adapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupViews();
    }

    @Override protected void onResume() {
        super.onResume();
        fetchFacts();
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
        return () -> {
            feedbackMessage.setVisibility(View.VISIBLE);
            feedbackMessage.setText(R.string.feedback_empty_state);
        };
    }

    @Override public Action hideEmptyState() {
        return () -> feedbackMessage.setVisibility(View.GONE);
    }

    @Override public Action showErrorState() {
        return () -> {
            feedbackMessage.setVisibility(View.VISIBLE);
            feedbackMessage.setText(R.string.feedback_error_state);
        };
    }

    @Override public Action hideErrorState() {
        return () -> feedbackMessage.setVisibility(View.GONE);
    }

    @Override public Action reportNetworkingError() {
        return () ->
                Snackbar.make(container, "Erro de conexão. Por favor, tente novamente", Snackbar.LENGTH_INDEFINITE)
                        .setAction("TENTAR NOVAMENTE", view -> fetchFacts())
                        .show();
    }

    @Override public Disposable subscribeInto(Flowable<FactViewModel> flow) {
        adapter.clear();
        return flow
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        model -> adapter.addModel(model),
                        throwable -> Log.e(TAG, "Error -> " + throwable.getMessage()),
                        () -> Log.i(TAG, "Done")
                );
    }

    private void setupViews() {
        fab.setOnClickListener(view -> fetchFacts());
        adapter = new FactsAdapter(LayoutInflater.from(this));
        factsView.setLayoutManager(new LinearLayoutManager(this));
        factsView.setAdapter(adapter);
    }

    private void fetchFacts() {
        if (presenter != null) presenter.fetchRandomFacts();
    }

    @Override public Action disableRefresh() {
        return () -> fab.setVisibility(View.GONE);
    }

    @Override public Action enableRefresh() {
        return () -> fab.setVisibility(View.VISIBLE);
    }
}
