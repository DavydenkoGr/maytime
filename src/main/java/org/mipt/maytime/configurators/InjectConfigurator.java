package org.mipt.maytime.configurators;

import java.lang.reflect.Field;
import org.mipt.maytime.Context;
import org.mipt.maytime.annotations.Inject;

/**
 * Configurator which fills fields with @Inject annotation
 *
 * @author Davydenko Grigorii
 * @author Vronskii Alexander
 */
public class InjectConfigurator extends AbstractConfigurator {
    /**
     * InjectConfigurator constructor
     * @param context application context
     */
    public InjectConfigurator(Context context) {
        super(context);
    }

    /**
     * Fill all annotated object fields
     * @param object configurable object
     */
    @Override
    public <T> void configure(T object) {
        // проходимся по полям каждого из бинов в поисках полей с аннотацией @Inject ->
        // -> вновь проходимся по бинам в поисках совпадения
        for (Field field: object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                try {
                    Object dependency = context.get(field.getType());
                    // Установка полей
                    field.setAccessible(true);
                    field.set(object, dependency);
                    field.setAccessible(false);
                } catch (IllegalAccessException ignored) {

                }
            }
        }
    }
}
