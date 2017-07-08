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
    private ForegroundColorSpan factColor;


    public ComposedWithSpannedStyles(String number,
                                     String fact,
                                     ForegroundColorSpan numberColor,
                                     ForegroundColorSpan factColor) {
        this.number = number;
        this.fact = fact;
        this.numberColor = numberColor;
        this.factColor = factColor;
    }

    @Override public CharSequence formattedFact() {

        return new Spanny()
                .append(number, new StyleSpan(Typeface.BOLD), numberColor)
                .append(fact, factColor)
                ;
    }

}
