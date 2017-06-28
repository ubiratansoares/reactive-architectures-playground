package br.ufs.demos.rxmvp.playground.trivia.domain;

import org.immutables.value.Value;

import br.ufs.demos.rxmvp.playground.util.WithFactoryPattern;

/**
 * Created by bira on 6/26/17.
 */

@Value.Immutable
@WithFactoryPattern
public interface FactAboutNumber {

    String number();

    String fact();

}
