package br.ufs.demos.rxmvp.playground;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.ufs.demos.rxmvp.playground.trivia.ui.FactsAboutNumbersActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

/**
 * Created by bira on 10/3/17.
 */

@RunWith(AndroidJUnit4.class)
public class TriviaAcceptanceTests {

    @Rule public ActivityTestRule<FactsAboutNumbersActivity> rule =
            new ActivityTestRule(FactsAboutNumbersActivity.class);


    @Test public void atFirstLoad_ViewsVisibilityAreCorrect() {
        onView(withId(R.id.fab)).check(matches(not(isDisplayed())));
    }

}
