package br.ufs.demos.rxmvp.playground.trivia;

import android.view.View;
import android.widget.ProgressBar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

import br.ufs.demos.rxmvp.playground.BuildConfig;
import br.ufs.demos.rxmvp.playground.R;
import br.ufs.demos.rxmvp.playground.app.MainApplication;
import br.ufs.demos.rxmvp.playground.trivia.presentation.models.FactViewModel;
import br.ufs.demos.rxmvp.playground.trivia.presentation.models.NumberAndFact;
import br.ufs.demos.rxmvp.playground.trivia.ui.FactsAboutNumbersActivity;
import br.ufs.demos.rxmvp.playground.trivia.ui.FactsAdapter;
import io.reactivex.Flowable;

import static butterknife.ButterKnife.findById;
import static org.assertj.core.api.Assertions.assertThat;
import static org.robolectric.Robolectric.buildActivity;

/**
 * Created by bira on 7/4/17.
 */

@RunWith(RobolectricTestRunner.class)
@Config(
        constants = BuildConfig.class,
        application = MainApplication.class,
        sdk = 25,
        packageName = "br.ufs.demos.rxmvp.playground"
)
public class FactsAboutNumbersActivityTest {

    FactsAboutNumbersActivity activity;

    @Before public void beforeEachTest() {
        activity = buildActivity(FactsAboutNumbersActivity.class).create().get();
    }

    @Test public void setupViewsProperly_OnCreate() {
        ProgressBar loading = findById(activity, R.id.progressBar);
        assertThat(loading.getVisibility()).isEqualTo(View.GONE);

        View labelMessage = findById(activity, R.id.label_feedback_message);
        assertThat(labelMessage.getVisibility()).isEqualTo(View.GONE);
    }

    @Test public void shoulIntegrateActions_ForLoadingVisibility() throws Exception {
        ProgressBar loading = findById(activity, R.id.progressBar);

        activity.showLoading().run();
        assertThat(loading.getVisibility()).isEqualTo(View.VISIBLE);

        activity.hideLoading().run();
        assertThat(loading.getVisibility()).isEqualTo(View.GONE);
    }

    @Test public void shoulIntegrateActions_ForErrorState() throws Exception {
        View labelMessage = findById(activity, R.id.label_feedback_message);

        activity.showErrorState().run();
        assertThat(labelMessage.getVisibility()).isEqualTo(View.VISIBLE);

        activity.hideErrorState().run();
        assertThat(labelMessage.getVisibility()).isEqualTo(View.GONE);
    }

    @Test public void shoulIntegrateActions_ForEmptyState() throws Exception {
        View labelMessage = findById(activity, R.id.label_feedback_message);

        activity.showEmptyState().run();
        assertThat(labelMessage.getVisibility()).isEqualTo(View.VISIBLE);

        activity.hideEmptyState().run();
        assertThat(labelMessage.getVisibility()).isEqualTo(View.GONE);
    }

    @Test public void shoulIntegrateAction_ForNetworkingErrorFeedback() throws Exception {

        activity.reportNetworkingError().run();

        // From design_layout_snackbar_include.xml, since Snackbar does not
        // have an id assigned at his own container
        View snackText = findById(activity, R.id.snackbar_text);

        assertThat(snackText).isNotNull();
        assertThat(snackText.getVisibility()).isEqualTo(View.VISIBLE);
    }

    @Test public void shoulIntegrateActions_ForRefreshToogle() throws Exception {

        View fab = findById(activity, R.id.fab);

        activity.disableRefresh().run();
        assertThat(fab.getVisibility()).isEqualTo(View.GONE);

        activity.enableRefresh().run();
        assertThat(fab.getVisibility()).isEqualTo(View.VISIBLE);
    }

    @Test public void shoulIntegrate_DataDispatching_AvailableData() throws Exception {

        View labelMessage = findById(activity, R.id.label_feedback_message);

        List<FactViewModel> facts = Arrays.asList(
                new NumberAndFact("1", "1 is the first"),
                new NumberAndFact("2", "2 is the second")
        );

        Flowable<FactViewModel> dataFlow = Flowable.fromIterable(facts);
        FactsAdapter adapter = activity.adapter;

        activity.subscribeInto(dataFlow);

        assertThat(adapter.getItemCount()).isEqualTo(facts.size());
        assertThat(labelMessage.getVisibility()).isEqualTo(View.GONE);

    }

    @Test public void shoulIntegrate_DataDispatching_NoData() throws Exception {

        View labelMessage = findById(activity, R.id.label_feedback_message);

        Flowable<FactViewModel> dataFlow = Flowable.empty();
        FactsAdapter adapter = activity.adapter;

        activity.subscribeInto(dataFlow);

        assertThat(adapter.getItemCount()).isEqualTo(0);
        assertThat(labelMessage.getVisibility()).isEqualTo(View.GONE);

    }
}
