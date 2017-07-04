package br.ufs.demos.rxmvp.playground.ui;

import android.view.View;
import android.widget.ProgressBar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import br.ufs.demos.rxmvp.playground.BuildConfig;
import br.ufs.demos.rxmvp.playground.R;
import br.ufs.demos.rxmvp.playground.app.MainApplication;
import br.ufs.demos.rxmvp.playground.trivia.ui.FactsAboutNumbersActivity;

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
        sdk = 25
)
public class FactsAboutNumbersActivityTest {

    FactsAboutNumbersActivity activity;

    @Before public void beforeEachTest() {
        activity = buildActivity(FactsAboutNumbersActivity.class).create().get();
    }

    @Test public void shouldSetupViewsProperly_OnCreate() {
        ProgressBar loading = findById(activity, R.id.progressBar);
        assertThat(loading.getVisibility()).isEqualTo(View.GONE);

        View labelMessage = findById(activity, R.id.label_feedback_message);
        assertThat(labelMessage.getVisibility()).isEqualTo(View.GONE);
    }

    @Test public void shoulIntegrate_ActionsForLoadingVisibility() throws Exception {
        ProgressBar loading = findById(activity, R.id.progressBar);

        activity.showLoading().run();
        assertThat(loading.getVisibility()).isEqualTo(View.VISIBLE);

        activity.hideLoading().run();
        assertThat(loading.getVisibility()).isEqualTo(View.GONE);
    }

    @Test public void shoulIntegrate_ActionsForErrorState() throws Exception {
        View labelMessage = findById(activity, R.id.label_feedback_message);

        activity.showErrorState().run();
        assertThat(labelMessage.getVisibility()).isEqualTo(View.VISIBLE);

        activity.hideErrorState().run();
        assertThat(labelMessage.getVisibility()).isEqualTo(View.GONE);
    }

    @Test public void shoulIntegrate_ActionsForErrorEmptyState() throws Exception {
        View labelMessage = findById(activity, R.id.label_feedback_message);

        activity.showEmptyState().run();
        assertThat(labelMessage.getVisibility()).isEqualTo(View.VISIBLE);

        activity.hideEmptyState().run();
        assertThat(labelMessage.getVisibility()).isEqualTo(View.GONE);
    }

}
