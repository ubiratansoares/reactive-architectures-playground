package br.ufs.demos.rxmvp.playground.util;

import org.immutables.value.Value;

/**
 * Created by bira on 6/26/17.
 */

@Value.Style(
        allParameters = true,
        visibility = Value.Style.ImplementationVisibility.PUBLIC,
        defaults = @Value.Immutable(builder = false, copy = false)
)
public @interface WithFactoryPattern {

}
