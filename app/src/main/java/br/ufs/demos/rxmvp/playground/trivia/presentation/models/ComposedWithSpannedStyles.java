package br.ufs.demos.rxmvp.playground.trivia.presentation.models;

import android.graphics.Typeface;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.binaryfork.spanny.Spanny;

/**
 * Created by bira on 7/8/17.
 */

public class ComposedWithSpannedStyles implements FactViewModel {

    private String number;
    private String fact;
    private ForegroundColorSpan numberColor;


    private ComposedWithSpannedStyles(String number,
                                      String fact,
                                      ForegroundColorSpan numberColor) {
        this.number = number;
        this.fact = fact;
        this.numberColor = numberColor;
    }

    public static ComposedWithSpannedStyles with(String number,
                                                 String fact,
                                                 ForegroundColorSpan numberColor) {

        return new ComposedWithSpannedStyles(number, fact, numberColor);
    }

    @Override public CharSequence formattedFact() {

        return new Spanny()
                .append(number, new StyleSpan(Typeface.BOLD), numberColor)
                .append(fact);
    }

    @Override public int viewType() {
        return TYPE_SINGLE_LABEL;
    }

}
