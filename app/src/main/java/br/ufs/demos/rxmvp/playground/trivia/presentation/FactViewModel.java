package br.ufs.demos.rxmvp.playground.trivia.presentation;

/**
 * Created by bira on 6/29/17.
 */

public class FactViewModel {

    public String number;
    public String fact;

    public FactViewModel(String number, String fact) {
        this.number = number;
        this.fact = fact;
    }

    @Override public String toString() {
        return "FactViewModel{" +
                "number='" + number + '\'' +
                ", fact='" + fact + '\'' +
                '}';
    }
}
