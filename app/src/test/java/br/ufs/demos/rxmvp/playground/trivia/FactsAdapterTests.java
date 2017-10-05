package br.ufs.demos.rxmvp.playground.trivia;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

import br.ufs.demos.rxmvp.playground.BuildConfig;
import br.ufs.demos.rxmvp.playground.app.MainApplication;
import br.ufs.demos.rxmvp.playground.trivia.presentation.models.ComposedWithSpannedStyles;
import br.ufs.demos.rxmvp.playground.trivia.presentation.models.FactViewModel;
import br.ufs.demos.rxmvp.playground.trivia.presentation.models.NumberAndFact;
import br.ufs.demos.rxmvp.playground.trivia.ui.FactsAboutNumbersActivity;
import br.ufs.demos.rxmvp.playground.trivia.ui.FactsAdapter;
import br.ufs.demos.rxmvp.playground.trivia.ui.FactsAdapter.SingleLabelHolder;
import br.ufs.demos.rxmvp.playground.trivia.ui.FactsAdapter.TwoLabelsHolder;
import ix.Ix;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.robolectric.Robolectric.buildActivity;

/**
 * Created by bira on 10/5/17.
 */

@RunWith(RobolectricTestRunner.class)
@Config(
        constants = BuildConfig.class,
        application = MainApplication.class,
        sdk = 25,
        packageName = "br.ufs.demos.rxmvp.playground"
)
public class FactsAdapterTests {

    List<FactViewModel> facts = Arrays.asList(
            new NumberAndFact("1", "1 is the first"),
            new NumberAndFact("2", "2 is the second"),
            ComposedWithSpannedStyles.with(
                    "157",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod",
                    new ForegroundColorSpan(Color.BLUE)
            )
    );

    FactsAdapter adapter;
    RecyclerView recyclerView;

    @Before public void setup() {
        FactsAboutNumbersActivity hostActivity =
                buildActivity(FactsAboutNumbersActivity.class)
                        .create()
                        .get();

        adapter = new FactsAdapter(LayoutInflater.from(hostActivity));
        recyclerView = hostActivity.factsView;
        fillAdapter();
    }

    @Test public void shouldCrash_WhenDataAvailable_ForViewTypes() {
        Activity host = buildActivity(FactsAboutNumbersActivity.class).create().get();
        adapter = new FactsAdapter(LayoutInflater.from(host));

        assertThat(adapter.getItemCount()).isEqualTo(0);

        ThrowingCallable call = () -> adapter.getItemViewType(0);
        assertThatThrownBy(call).isInstanceOf(IllegalStateException.class);
    }

    @Test public void shouldObtain_ViewType_WhenDataAvailable() {
        assertThat(adapter.getItemViewType(0)).isEqualTo(FactViewModel.TYPE_TWO_LABELS);
        assertThat(adapter.getItemViewType(2)).isEqualTo(FactViewModel.TYPE_SINGLE_LABEL);
    }

    @Test public void shouldCreateViewHolder_AsSingleLabel() {
        RecyclerView.ViewHolder viewHolder = adapter.onCreateViewHolder(
                recyclerView,
                FactViewModel.TYPE_SINGLE_LABEL
        );

        assertThat(viewHolder).isInstanceOf(SingleLabelHolder.class);
    }

    @Test public void shouldCreateViewHolder_AsTwoLabels() {
        RecyclerView.ViewHolder viewHolder = adapter.onCreateViewHolder(
                recyclerView,
                FactViewModel.TYPE_TWO_LABELS
        );

        assertThat(viewHolder).isInstanceOf(TwoLabelsHolder.class);
    }

    @Test public void shouldNotCreateViewHolder_ForInvalidViewType() {
        ThrowingCallable call = () -> adapter.onCreateViewHolder(recyclerView, Integer.MAX_VALUE);
        assertThatThrownBy(call).isInstanceOf(IllegalArgumentException.class);
    }

    @Test public void shouldBindViewHolder_AsTwoLabels() {

        int dataPosition = 0;

        TwoLabelsHolder viewHolder = (TwoLabelsHolder) adapter.onCreateViewHolder(
                recyclerView,
                FactViewModel.TYPE_TWO_LABELS
        );

        NumberAndFact model = (NumberAndFact) facts.get(dataPosition);
        adapter.onBindViewHolder(viewHolder, dataPosition);

        assertThat(viewHolder.labelNumber.getText()).isEqualTo(model.relatedNumber());
        assertThat(viewHolder.labelFact.getText()).isEqualTo(model.formattedFact());
    }

    @Test public void shouldBindViewHolder_AsSingleLabel() {

        int dataPosition = 2;

        SingleLabelHolder viewHolder = (SingleLabelHolder) adapter.onCreateViewHolder(
                recyclerView,
                FactViewModel.TYPE_SINGLE_LABEL
        );

        ComposedWithSpannedStyles model = (ComposedWithSpannedStyles) facts.get(dataPosition);
        adapter.onBindViewHolder(viewHolder, dataPosition);

        assertThat(viewHolder.singleLabel.getText()).contains(model.formattedFact());
    }


    private void fillAdapter() {
        Ix.from(facts).foreach(model -> adapter.addModel(model));
    }
}
