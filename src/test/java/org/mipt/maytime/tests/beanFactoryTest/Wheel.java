package org.mipt.maytime.tests.beanFactoryTest;

import org.mipt.maytime.annotations.Component;
import org.mipt.maytime.annotations.Inject;

@Component
public class Wheel {
    @Inject
    private Details details;

    public Details getDetails() {
        return details;
    }
}
