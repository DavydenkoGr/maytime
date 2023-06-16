package org.mipt.maytime.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mipt.maytime.App;
import org.mipt.maytime.Context;
import org.mipt.maytime.tests.beanFactoryTest.Details;
import org.mipt.maytime.tests.beanFactoryTest.Vehicle;
import org.mipt.maytime.tests.beanFactoryTest.Wheel;

public class ContextTest {
    
    @Test
    public void basicTest() {
        Context ctx = App.run("org.mipt.maytime.tests.beanFactoryTest");
        
        Vehicle vehicle = ctx.get(Vehicle.class);
        Wheel wheel = ctx.get(Wheel.class);
        Details details = ctx.get(Details.class);

        Assertions.assertSame(wheel.getDetails(), details);
        Assertions.assertSame(vehicle.getWheel().getDetails(), wheel.getDetails());

        App.restart("org.mipt.maytime.tests");

        vehicle = ctx.get(Vehicle.class);
        wheel = ctx.get(Wheel.class);
        details = ctx.get(Details.class);

        Assertions.assertSame(wheel.getDetails(), details);
        Assertions.assertSame(vehicle.getWheel().getDetails(), wheel.getDetails());
    }
}
