package br.ufs.demos.rxmvp.playground.util;

/**
 * Created by bira on 7/8/17.
 */

public interface DomainToViewModel<Data, ViewModel> {

    ViewModel translate(Data data);

}
