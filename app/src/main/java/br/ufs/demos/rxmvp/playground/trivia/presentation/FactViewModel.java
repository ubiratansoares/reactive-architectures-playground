package br.ufs.demos.rxmvp.playground.trivia.presentation;

/**
 * Created by bira on 6/29/17.
 */

public class FactViewModel {

    public static int DISPLAY_TYPE_HORIZONTAL = 0;
    public static int DISPLAY_TYPE_VERTICAL = 1;

    String number;
    String fact;
    int displayType;

    public FactViewModel(String number, String fact, int displayType) {
        this.number = number;
        this.fact = fact;
        this.displayType = displayType;
    }

    @Override public String toString() {
        return "FactViewModel{" +
                "number='" + number + '\'' +
                ", fact='" + fact + '\'' +
                ", displayType=" + displayType +
                '}';
    }
}
