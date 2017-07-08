package br.ufs.demos.rxmvp.playground.trivia.presentation;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.style.ForegroundColorSpan;

import br.ufs.demos.rxmvp.playground.R;
import br.ufs.demos.rxmvp.playground.trivia.domain.FactAboutNumber;
import br.ufs.demos.rxmvp.playground.trivia.presentation.models.ComposedWithSpannedStyles;
import br.ufs.demos.rxmvp.playground.trivia.presentation.models.FactViewModel;
import br.ufs.demos.rxmvp.playground.trivia.presentation.models.NumberAndFact;
import br.ufs.demos.rxmvp.playground.util.DomainToViewModel;

/**
 * Created by bira on 7/1/17.
 */

public class FactsViewModelMapper implements DomainToViewModel<FactAboutNumber, FactViewModel> {

    private static final int MAX_CHARS_FOR_SMALL_FACT = 50;
    private Context context;

    public FactsViewModelMapper(Context context) {
        this.context = context;
    }

    @Override public FactViewModel translate(FactAboutNumber fact) {
        if (isFactTooLarge(fact)) return composedWithSpans(fact);
        return asNumberAndFact(fact);
    }

    private FactViewModel asNumberAndFact(FactAboutNumber fact) {
        return new NumberAndFact(
                fact.number,
                formatFact(fact.number, fact.fact)
        );
    }

    private FactViewModel composedWithSpans(FactAboutNumber fact) {

        int accent = ContextCompat.getColor(context, R.color.colorAccent);

        return ComposedWithSpannedStyles.with(
                fact.number,
                " " + formatFact(fact.number, fact.fact),
                new ForegroundColorSpan(accent)
        );
    }

    private String formatFact(String number, String fact) {
        return fact.replace(number + " ", "");
    }

    private boolean isFactTooLarge(FactAboutNumber fact) {
        String formatted = formatFact(fact.number, fact.fact);
        return formatted.length() > MAX_CHARS_FOR_SMALL_FACT;
    }
}
