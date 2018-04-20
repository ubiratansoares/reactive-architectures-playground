package br.ufs.demos.rxmvp.playground;

import static com.schibsted.spain.barista.BaristaAssertions.assertDisplayed;
import static com.schibsted.spain.barista.BaristaAssertions.assertNotDisplayed;
import static com.schibsted.spain.barista.BaristaAssertions.assertRecyclerViewItemCount;

/**
 * Created by bira on 10/3/17.
 */

public class Verifier {

//    @BindView(R.id.label_feedback_message) TextView feedbackMessage;
//    @BindView(R.id.progressBar) ProgressBar loading;
//    @BindView(R.id.recyclerview_facts) RecyclerView factsView;
//    @BindView(R.id.fab) FloatingActionButton fab;

    public Verifier refreshButtonShouldBeAvailable() {
        assertDisplayed(R.id.fab);
        return this;
    }

    public Verifier refreshButtonShouldNotBeAvailable() {
        assertNotDisplayed(R.id.fab);
        return this;
    }

    public Verifier loadingIndicatorShouldBeInvisible() {
        assertNotDisplayed(R.id.progressBar);
        return this;
    }

    public Verifier serverErrorReported() {
        assertDisplayed(R.id.label_feedback_message);
        assertDisplayed(R.string.feedback_error_state);
        return this;
    }

    public Verifier intenetIssueReported() {
//        assertDisplayed(R.id.snackbar_text);
//        assertDisplayed(R.string.feedback_message_internet_issue);
        // TODO: fix snackbar text testing
        // android.support.test.espresso.base.DefaultFailureHandler$AssertionFailedWithCauseError: 'is displayed on the screen to the user' doesn't match the selected view.
        //Expected: is displayed on the screen to the user
        //Got: "AppCompatTextView{id=2131230863, res-name=snackbar_text, visibility=VISIBLE, width=445, height=71, has-focus=false, has-focusable=false, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=false, is-selected=false, layout-params=android.widget.LinearLayout$LayoutParams@e2903e3, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=0.0, text=Erro de conexão. Por favor, tente novamente, input-type=0, ime-target=false, has-links=false}"
        // tried android.support.design.R.id.snackbar_text but didn't work too

        // using snackbar action works well, but we could not have same action text in different snackbars
        assertDisplayed(R.id.snackbar_action);
        assertDisplayed(R.string.feedback_action_internet_issue);
        return this;
    }

    public Verifier noErrors() {
        assertNotDisplayed(R.id.label_feedback_message);
        return this;
    }

    public Verifier noResultsReported() {
        assertDisplayed(R.id.label_feedback_message);
        assertDisplayed(R.string.feedback_empty_state);
        return this;
    }

    public Verifier noTriviaItemsDisplayed() {
        assertRecyclerViewItemCount(R.id.recyclerview_facts, 0);
        return this;
    }

    public Verifier triviaItemsDisplayed(int quantity) {
        assertRecyclerViewItemCount(R.id.recyclerview_facts, quantity);
        return this;
    }
}
