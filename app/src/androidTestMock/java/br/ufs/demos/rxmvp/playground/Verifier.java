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
        assertDisplayed(R.id.snackbar_text);
        assertDisplayed(R.string.feedback_message_internet_issue);
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
