package org.mipt.maytime;

import jakarta.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * Application which runs and restarts context
 *
 * @author Davydenko Grigorii
 * @author Vronskii Alexander
 */
public final class App {
    /**
     * Empty App constructor
     */
    private App() {
    }

    /**
     * Run context
     * @param sources name of the packages controlled by context
     * @return context instance
     */
    @NotNull
    public static Context run(List<String> sources) {
        Context context = Context.getInstance(sources);
        context.load();
        context.instantiate();
        context.configure();
        return context;
    }

    /**
     * Run context
     * @param source name of the package controlled by context
     * @return context instance
     */
    @NotNull
    public static Context run(String source) {
        return run(Arrays.asList(source));
    }

    /**
     * Run context
     * @param sourceClazz class of the package controlled by context
     * @return context instance
     */
    public static Context run(Object sourceClazz) {
        return run(sourceClazz.getClass().getPackage().getName());
    }

    /**
     * Clear current context session and restart context
     * @param sources name of the packages controlled by context
     * @return instance context instance
     */
    @NotNull
    public static Context restart(List<String> sources) {
        Context.clear();
        return run(sources);
    }

    /**
     * Clear current context session and restart context
     * @param source name of the package controlled by context
     * @return instance context instance
     */
    @NotNull
    public static Context restart(String source) {
        Context.clear();
        return run(source);
    }

    /**
     * Clear current context session and restart context
     * @param sourceClazz class of the package controlled by context
     * @return instance context instance
     */
    @NotNull
    public static Context restart(Object sourceClazz) {
        Context.clear();
        return run(sourceClazz.getClass().getPackage().getName());
    }
}
