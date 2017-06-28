package br.ufs.demos.rxmvp.playground.util;

import java.util.Collection;

/**
 * Created by bira on 6/28/17.
 */

public class Checks {

    public static boolean notNullNotEmpty(Collection<?> objects) {
        return objects != null && !objects.isEmpty();
    }
}
