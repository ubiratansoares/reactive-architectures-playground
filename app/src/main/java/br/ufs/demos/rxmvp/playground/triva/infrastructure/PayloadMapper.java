package br.ufs.demos.rxmvp.playground.triva.infrastructure;


import java.util.List;

import br.ufs.demos.rxmvp.playground.triva.domain.FactAboutNumber;
import br.ufs.demos.rxmvp.playground.triva.domain.ImmutableFactAboutNumber;
import ix.Ix;

/**
 * Created by bira on 6/26/17.
 */

public class PayloadMapper {

    public List<FactAboutNumber> toNumberFacts(NumbersTriviaPayload payload) {

        return Ix.from(payload.entrySet())
                .map(entry -> ImmutableFactAboutNumber.of(entry.getKey(), entry.getValue()))
                .cast(FactAboutNumber.class)
                .toList();
    }

}
