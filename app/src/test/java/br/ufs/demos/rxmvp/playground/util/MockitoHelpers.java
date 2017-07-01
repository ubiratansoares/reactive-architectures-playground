package br.ufs.demos.rxmvp.playground.util;

import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.verification.VerificationMode;

/**
 * Created by bira on 6/30/17.
 */

public class MockitoHelpers {

    public static VerificationMode onlyOnce() {
        return VerificationModeFactory.atMost(1);
    }
}
