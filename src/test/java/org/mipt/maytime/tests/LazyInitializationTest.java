package org.mipt.maytime.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mipt.maytime.App;
import org.mipt.maytime.Context;
import org.mipt.maytime.tests.beanFactoryTest.Wheel;
import org.mipt.maytime.tests.lazy.LazyDetails;
import org.mipt.maytime.tests.lazy.LazyVehicle;
import org.mipt.maytime.tests.lazy.LazyWheel;

public class LazyInitializationTest {
    
    @Test
    public void singleClassLazyInitialization() {
        Context ctx = App.run("org.mipt.maytime.tests.lazy");

        ctx.get(LazyDetails.class);
    }

    @Test
    public void classWithDependencies() {
        Context ctx = App.run("org.mipt.maytime.tests.lazy");

        LazyVehicle vehicle = ctx.get(LazyVehicle.class);
        LazyWheel wheel = ctx.get(LazyWheel.class);
        LazyDetails details = ctx.get(LazyDetails.class);

        Assertions.assertSame(wheel.getDetails(), details);
        Assertions.assertSame(vehicle.getWheel().getDetails(), wheel.getDetails());
    }

    @Test
    public void getUnloadedClassThrowsRuntimeException() {
        Context ctx = App.run("org.mipt.maytime.tests.lazy");

        Assertions.assertThrows(RuntimeException.class, () -> ctx.get(Wheel.class));
    }
}
