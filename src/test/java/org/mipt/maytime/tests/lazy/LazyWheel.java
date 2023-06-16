package org.mipt.maytime.tests.lazy;

import org.mipt.maytime.annotations.Component;
import org.mipt.maytime.annotations.Inject;
import org.mipt.maytime.annotations.LazyInitialization;

@Component
@LazyInitialization
public class LazyWheel {
    @Inject
    private LazyDetails details;

    public LazyDetails getDetails() {
        return details;
    }
}
