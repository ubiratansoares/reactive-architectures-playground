package br.ufs.demos.rxmvp.playground.trivia.domain;

/**
 * Created by bira on 6/26/17.
 */

public class FactAboutNumber {

    public String number;
    public String fact;

    private FactAboutNumber(String number, String fact) {
        this.number = number;
        this.fact = fact;
    }

    public static FactAboutNumber of(String number, String fact) {
        return new FactAboutNumber(number, fact);
    }
}
