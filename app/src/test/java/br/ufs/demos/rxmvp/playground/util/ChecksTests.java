package br.ufs.demos.rxmvp.playground.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by bira on 10/5/17.
 */

public class ChecksTests {

    @Test public void checkNull() {
        List<?> nullList = null;
        assertThat(Checks.notNullNotEmpty(nullList)).isFalse();
    }

    @Test public void checkEmpty() {
        List<?> empty = Collections.emptyList();
        assertThat(Checks.notNullNotEmpty(empty)).isFalse();
    }

    @Test public void checkRegular() {
        List<String> items = Arrays.asList("A", "B");
        assertThat(Checks.notNullNotEmpty(items)).isTrue();
    }

}
