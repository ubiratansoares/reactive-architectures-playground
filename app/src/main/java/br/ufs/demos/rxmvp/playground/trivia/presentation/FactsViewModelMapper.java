package br.ufs.demos.rxmvp.playground.trivia.presentation;

import br.ufs.demos.rxmvp.playground.trivia.domain.FactAboutNumber;
import br.ufs.demos.rxmvp.playground.trivia.presentation.models.FactViewModel;
import br.ufs.demos.rxmvp.playground.trivia.presentation.models.NumberAndFact;
import br.ufs.demos.rxmvp.playground.util.DomainToViewModel;

/**
 * Created by bira on 7/1/17.
 */

public class FactsViewModelMapper implements DomainToViewModel<FactAboutNumber, FactViewModel> {

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
        return null;
    }

    private String formatFact(String number, String fact) {
        return fact.replace(number + " ", "");
    }

    private boolean isFactTooLarge(FactAboutNumber fact) {
        return false;
    }
}
