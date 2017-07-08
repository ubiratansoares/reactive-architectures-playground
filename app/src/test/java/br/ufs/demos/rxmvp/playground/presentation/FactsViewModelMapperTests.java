package br.ufs.demos.rxmvp.playground.presentation;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import br.ufs.demos.rxmvp.playground.BuildConfig;
import br.ufs.demos.rxmvp.playground.app.MainApplication;
import br.ufs.demos.rxmvp.playground.trivia.domain.FactAboutNumber;
import br.ufs.demos.rxmvp.playground.trivia.presentation.FactsViewModelMapper;
import br.ufs.demos.rxmvp.playground.trivia.presentation.models.ComposedWithSpannedStyles;
import br.ufs.demos.rxmvp.playground.trivia.presentation.models.FactViewModel;
import br.ufs.demos.rxmvp.playground.trivia.presentation.models.NumberAndFact;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by bira on 7/8/17.
 */

@RunWith(RobolectricTestRunner.class)
@Config(
        constants = BuildConfig.class,
        application = MainApplication.class,
        sdk = 25
)
public class FactsViewModelMapperTests {

    FactsViewModelMapper mapper;

    @Before public void beforeEachTest() {
        Context context = RuntimeEnvironment.application;
        mapper = new FactsViewModelMapper(context);
    }

    @Test public void shouldMap_UpTo50Chars_AsTwoLabelsModel() {
        String text = "A text that should have 49 chars at most, no more"; // 49 chars
        FactAboutNumber fact = FactAboutNumber.of("1", text);
        FactViewModel model = mapper.translate(fact);
        assertThat(model).isInstanceOf(NumberAndFact.class);
    }

    @Test public void shouldMap_Above50Chars_AsSingleLabelModel() {
        String text = "Another text that is much, much larger than 49 characters"; // 57 chars
        FactAboutNumber fact = FactAboutNumber.of("17", text);
        FactViewModel model = mapper.translate(fact);
        assertThat(model).isInstanceOf(ComposedWithSpannedStyles.class);
    }

    @Test public void shouldSplit_NumberFrom_FactText() {
        String text = "7 is a number"; // 49 chars
        FactAboutNumber fact = FactAboutNumber.of("7", text);
        FactViewModel model = mapper.translate(fact);
        assertThat(model.formattedFact()).doesNotContain("7");
    }
}

