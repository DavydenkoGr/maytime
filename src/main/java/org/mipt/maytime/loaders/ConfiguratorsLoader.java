package org.mipt.maytime.loaders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.mipt.maytime.Context;
import org.mipt.maytime.configurators.AbstractConfigurator;
import org.mipt.maytime.exeptions.CreateConfiguratorException;

/**
 * Util which find all maytime configurators in specified package
 *
 * @author Davyedenko Grigorii
 * @author Vronskii Alexander
 */
public class ConfiguratorsLoader {
    private final String packageName;
    private final Context context;

    /**
     * ConfiguratorsLoader constructor
     * @param packageName name os scanning package
     * @param context application context
     */
    public ConfiguratorsLoader(String packageName, Context context) {
        this.packageName = packageName;
        this.context = context;
    }

    /**
     * Create class by name
     * @param className name of the returning class
     * @return class
     * @throws CreateConfiguratorException if it cannot create class by class name
     */
    private Class<?> getClass(String className) {
        try {
            return Class.forName(packageName + "." + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            throw new CreateConfiguratorException("Cannot find configurator class=" + className);
        }
    }

    /**
     * Checks if configurator meets the requirements
     * @param clazz configurator class
     * @return if configurator correct
     */
    private boolean isValidConfigurator(Class<?> clazz) {
        return AbstractConfigurator.class.isAssignableFrom(clazz)
            && !Modifier.isAbstract(clazz.getModifiers())
            && !clazz.isInterface();
    }

    /**
     * Create configurator instance
     * @param clazz configurator class
     * @return configurator instance
     * @throws CreateConfiguratorException if it cannot create configurator instance
     */
    private AbstractConfigurator createConfigurator(Class<?> clazz) {
        try {
            return (AbstractConfigurator) clazz.getDeclaredConstructor(Context.class).newInstance(context);
        } catch (NoSuchMethodException | InstantiationException
                 | IllegalAccessException | InvocationTargetException exception) {
            throw new CreateConfiguratorException("Cannot create configurator by class=" + clazz.getName());
        }
    }

    /**
     * Load configurators from specified package and return configurators list
     * @return configurators list
     * @throws FileNotFoundException if it cannot find configurators package
     */
    public List<AbstractConfigurator> load() throws FileNotFoundException {
        InputStream stream = ClassLoader.getSystemClassLoader().
                getResourceAsStream(packageName.replaceAll("[.]", "/"));

        if (stream == null) {
            throw new FileNotFoundException("Cannot find package " + packageName);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines().
                filter(line -> line.endsWith(".class")).
                map(this::getClass).
                filter(configurator -> configurator != null && isValidConfigurator(configurator)).
                map(this::createConfigurator).
                filter(Objects::nonNull).
                collect(Collectors.toList());
    }
}
