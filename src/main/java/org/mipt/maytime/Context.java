package org.mipt.maytime;

import jakarta.validation.constraints.NotNull;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.mipt.maytime.annotations.Configuration;
import org.mipt.maytime.annotations.LazyInitialization;
import org.mipt.maytime.configurators.AbstractConfigurator;
import org.mipt.maytime.configurators.InjectConfigurator;
import org.mipt.maytime.exeptions.BeanNotFoundException;
import org.mipt.maytime.exeptions.RepeatingBeanException;
import org.mipt.maytime.loaders.ComponentLoader;

/**
 * Main user interface which helps to organize work with maytime framework.
 * Return the instances of annotated classes with ready dependencies
 *
 * @author Davydenko Grigorii
 * @author Vronskii Alexander
 */
public final class Context {
    private static Context instance = null;
    private final Set<Class<?>> components;
    private final Set<AbstractConfigurator> configurators;
    private final Map<Class<?>, Object> componentsMap;
    private final List<String> sources;

    /**
     * Private context constructor
     * @param sources name of the packages controlled by context
     */
    private Context(List<String> sources) {
        this.sources = sources;
        this.components = new HashSet<>();
        this.configurators = new HashSet<>();
        this.componentsMap = new HashMap<>();
    }

    /**
     * Return current context instance
     * @param sources name of the packages controlled by context
     * @return context instance
     */
    @NotNull
    static Context getInstance(List<String> sources) {
        if (instance != null) {
            return instance;
        }

        return new Context(sources);
    }

    /**
     * Delete current context instance
     */
    static void clear() {
        instance = null;
    }

    /**
     * Load all configurators and components and save them to set
     */
    public void load() {
        configurators.add(new InjectConfigurator(this));
        for (String source : sources) {
            components.addAll(ComponentLoader.load(source));
        }
    }

    /**
     * Create all beans and components without @LazyInitialization annotation.
     * Then put them into componentsMap
     */
    public void instantiate() {
        for (Class<?> clazz : components) {
            if (clazz.isAnnotationPresent(Configuration.class)) {
                for (AbstractMap.SimpleEntry<Class<?>, Object> entry : ComponentFactory.createSeveral(clazz)) {
                    put(entry.getKey(), entry.getValue());
                }
            } else if (!clazz.isAnnotationPresent(LazyInitialization.class)) {
                Object component = ComponentFactory.create(clazz);
                componentsMap.put(clazz, component);
            }
        }
    }

    /**
     * Run all context configurators
     */
    public void configure() {
        for (Object component : componentsMap.values()) {
            for (AbstractConfigurator configurator : configurators) {
                configurator.configure(component);
            }
        }
    }

    /**
     * Return service if it was created
     * otherwise it will try to find method in components list and create a new instance
     * @param service the desired service
     * @throws ClassCastException if there is a problem with type cast
     * @throws BeanNotFoundException if it cannot find appropriate bean in list
     */
    @SuppressWarnings("unchecked")
    @NotNull
    public <T> T get(Class<T> service) {
        if (componentsMap.containsKey(service)) {
            try {
                return (T) componentsMap.get(service);
            } catch (ClassCastException exception) {
                throw new ClassCastException("Miscast during get");
            }
        } else if (components.contains(service)) {
            T component = ComponentFactory.create(service);
            put(service, component);
            for (AbstractConfigurator configurator : configurators) {
                configurator.configure(component);
            }
            return component;
        } else {
            throw new BeanNotFoundException("No appropriate bean found");
        }
    }

    /**
     * Put service into componentsMap if absence
     * @param clazz service class
     * @param service service instance
     * @throws RepeatingBeanException if there is two instances of the same class
     */
    private void put(Class<?> clazz, Object service) {
        if (componentsMap.containsKey(clazz)) {
            throw new RepeatingBeanException("Two instances of the same class encountered: class=" + clazz.getName());
        } else {
            componentsMap.put(clazz, service);
        }
    }
}
