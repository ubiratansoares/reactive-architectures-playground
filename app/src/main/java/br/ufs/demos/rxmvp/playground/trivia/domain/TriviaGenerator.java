package br.ufs.demos.rxmvp.playground.trivia.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generate a list of numbers for new trivia
 */

public class TriviaGenerator {

    private static final int LARGER_NUMBER_TO_LEARN_ABOUT = 200;
    private static final int TRIVIA_COUNT = 10;
    private static final Random RANDOMIZER = new Random();

    private List<Integer> numbers = new ArrayList<>();

    public List<Integer> numberForTrivia() {

        while (numbers.size() < TRIVIA_COUNT) {

            int candidate = RANDOMIZER.nextInt(LARGER_NUMBER_TO_LEARN_ABOUT);

            if (!numbers.contains(candidate)) {
                numbers.add(candidate);
            }
        }

        return numbers;
    }

}
