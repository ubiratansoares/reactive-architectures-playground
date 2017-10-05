package br.ufs.demos.rxmvp.playground.trivia.presentation.models;

/**
 * Created by bira on 6/29/17.
 */

public class NumberAndFact implements FactViewModel {

    private String number;
    private String fact;

    public NumberAndFact(String number, String fact) {
        this.number = number;
        this.fact = fact;
    }
    
    @Override public CharSequence formattedFact() {
        return fact;
    }

    @Override public int viewType() {
        return TYPE_TWO_LABELS;
    }

    public String relatedNumber() {
        return number;
    }

}
