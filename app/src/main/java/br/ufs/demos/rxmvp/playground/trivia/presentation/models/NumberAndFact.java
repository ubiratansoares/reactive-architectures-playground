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

    @Override public String toString() {
        return "NumberAndFact{" +
                "number='" + number + '\'' +
                ", fact='" + fact + '\'' +
                '}';
    }

    @Override public CharSequence formattedFact() {
        return fact;
    }

    public String relatedNumber() {
        return number;
    }

}
