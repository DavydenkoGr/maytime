package org.mipt.maytime.configurators;

import org.mipt.maytime.Context;

/**
 * Abstract configurator
 *
 * @author Davydenko Grigorii
 * @author Vronskii Alexander
 */
public abstract class AbstractConfigurator {
    protected final Context context;

    /**
     * AbstractConfigurator constructor
     * @param context application context
     */
    public AbstractConfigurator(Context context) {
        this.context = context;
    }

    /**
     * Auto-detected configure method which configure object
     * @param object configurable object
     */
    public abstract <T> void configure(T object);
}
