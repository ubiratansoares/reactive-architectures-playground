package br.ufs.demos.rxmvp.playground;

import org.junit.Before;
import org.junit.Test;

import br.ufs.demos.rxmvp.playground.trivia.infrastructure.TriviaInfrastructure;
import br.ufs.demos.rxmvp.playground.trivia.infrastructure.PayloadMapper;


/**
 * Created by bira on 6/27/17.
 */

public class TriviaInfrastructureTests {

    TriviaInfrastructure infrastructure;

    @Before public void beforeEachTest() {
        PayloadMapper mapper = new PayloadMapper();
    }

    @Test public void shouldIntegrate_Response200OK() {
        // TODO
    }

}
