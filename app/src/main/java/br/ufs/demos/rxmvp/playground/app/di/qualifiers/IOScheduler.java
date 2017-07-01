package br.ufs.demos.rxmvp.playground.app.di.qualifiers;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by bira on 7/1/17.
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface IOScheduler {

}
