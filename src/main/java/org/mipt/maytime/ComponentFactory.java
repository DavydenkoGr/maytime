package org.mipt.maytime;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import org.mipt.maytime.annotations.Bean;
import org.mipt.maytime.exeptions.CreateBeanException;

/**
 * Factory which creates beans and components
 *
 * @author Vronskii Alexander
 */
public final class ComponentFactory {
    /**
     * Empty ComponentFactory constructor
     */
    private ComponentFactory() {
    }

    /**
     * Create empty objects by class
     * @param clazz class of the object being created
     * @return object instance
     * @throws CreateBeanException if bean constructor isn't empty
     */
    public static <T> T create(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException
                 | IllegalAccessException | InvocationTargetException exception) {
            throw new CreateBeanException("Error calling empty constructor of " + clazz.getName());
        }
    }

    /**
     * Instantiate class and run all methods annotated with @Bean
     * @param configurationClazz class with methods which create beans
     * @return list of created beans
     * @throws CreateBeanException if method annotated with @Bean cannot be called
     */
    public static List<AbstractMap.SimpleEntry<Class<?>, Object>> createSeveral(Class<?> configurationClazz) {
        Object configuration = create(configurationClazz);
        List<AbstractMap.SimpleEntry<Class<?>, Object>> beans = new ArrayList<>();
        for (Method method : configurationClazz.getMethods()) {
            try {
                if (method.isAnnotationPresent(Bean.class)) {
                    Object bean = method.invoke(configuration);
                    beans.add(new AbstractMap.SimpleEntry<>(bean.getClass(), bean));
                }
            } catch (IllegalAccessException | InvocationTargetException exception) {
                throw new CreateBeanException(
                    "Error creating bean configuration=" + configurationClazz.getName() + "method="
                        + method.getName()
                );
            }
        }

        return beans;
    }
}
