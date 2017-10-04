package br.ufs.demos.rxmvp.playground;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.ufs.demos.rxmvp.playground.trivia.ui.FactsAboutNumbersActivity;

import static br.ufs.demos.rxmvp.playground.fakes.FakeWebService.NextCall.CONNECTION_ERROR;
import static br.ufs.demos.rxmvp.playground.fakes.FakeWebService.NextCall.INTERNAL_SERVER_ERROR_5xy;
import static br.ufs.demos.rxmvp.playground.fakes.FakeWebService.NextCall.NOT_FOUND_404;
import static br.ufs.demos.rxmvp.playground.fakes.FakeWebService.NextCall.SUCCESS_200;

/**
 * Created by bira on 10/3/17.
 */

@RunWith(AndroidJUnit4.class)
public class TriviaAcceptanceTests {

    Verifier verifier;

    @Rule public ScriptableWebServerTestRule<FactsAboutNumbersActivity> launcher =
            new ScriptableWebServerTestRule(FactsAboutNumbersActivity.class);

    @Before public void setup() {
        verifier = new Verifier();
    }

    @Test public void serverDown() {
        launcher.startWithCenario(INTERNAL_SERVER_ERROR_5xy);

        verifier.refreshButtonShouldNotBeAvailable()
                .loadingIndicatorShouldBeInvisible()
                .noTriviaItemsDisplayed()
                .serverErrorReported();
    }

    @Test public void triviaNotFound() {
        launcher.startWithCenario(NOT_FOUND_404);

        verifier.refreshButtonShouldBeAvailable()
                .loadingIndicatorShouldBeInvisible()
                .noTriviaItemsDisplayed()
                .noResultsReported();
    }

    @Test public void connectionIssues() {
        launcher.startWithCenario(CONNECTION_ERROR);

        verifier.refreshButtonShouldBeAvailable()
                .loadingIndicatorShouldBeInvisible()
                .noTriviaItemsDisplayed()
                .intenetIssueReported();
    }

    @Test public void resultsAvailable() {
        launcher.startWithCenario(SUCCESS_200);

        verifier.refreshButtonShouldBeAvailable()
                .loadingIndicatorShouldBeInvisible()
                .noErrors()
                .triviaItemsDisplayed(6);
    }

}
