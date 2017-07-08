package br.ufs.demos.rxmvp.playground.trivia.presentation;

import br.ufs.demos.rxmvp.playground.trivia.domain.FactAboutNumber;

/**
 * Created by bira on 7/1/17.
 */

public class ViewModelMapper {

    public FactViewModel translateFrom(FactAboutNumber info) {
        return new FactViewModel(
                info.number,
                defineFact(info.number, info.fact)
        );
    }

    private String defineFact(String number, String fact) {
        return fact.replace(number + " ", "");
    }

}
