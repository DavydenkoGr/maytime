package org.mipt.maytime.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mipt.maytime.App;
import org.mipt.maytime.Context;

public class ConfigurationTest {

    @Test
    public void basicTest() {
        Context ctx = App.run("org.mipt.maytime.tests.configuration");

        Assertions.assertEquals(33, ctx.get(Integer.class));
    }
}
