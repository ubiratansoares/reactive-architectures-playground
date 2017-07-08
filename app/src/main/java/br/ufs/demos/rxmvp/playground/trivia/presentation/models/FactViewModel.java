package br.ufs.demos.rxmvp.playground.trivia.presentation.models;

/**
 * Created by bira on 7/8/17.
 */

public interface FactViewModel {

    int TYPE_TWO_LABELS = 0;
    int TYPE_SINGLE_LABEL = 1;

    CharSequence formattedFact();

    int viewType();

}
