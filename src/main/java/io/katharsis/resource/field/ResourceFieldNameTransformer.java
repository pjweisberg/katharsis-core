package io.katharsis.resource.field;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.katharsis.utils.ClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Returns a name of a field. It takes into account {@link JsonProperty} annotation.
 */
public class ResourceFieldNameTransformer {

    public String getName(Field field) {
        String name = field.getName();
        if (field.isAnnotationPresent(JsonProperty.class) &&
            !"".equals(field.getAnnotation(JsonProperty.class).value())) {
            name = field.getAnnotation(JsonProperty.class).value();
        }
        return name;
    }

    public String getName(Method method) {
        String name;
        if (ClassUtils.isBooleanGetter(method)) {
            name = extractMethodName(method, 2);
        } else {
            name = extractMethodName(method, 3);
        }

        if (method.isAnnotationPresent(JsonProperty.class) &&
            !"".equals(method.getAnnotation(JsonProperty.class).value())) {
            name = method.getAnnotation(JsonProperty.class).value();
        }
        return name;
    }

    private String extractMethodName(Method method, int nameStart) {
        String resourceName = method.getName().substring(nameStart);
        return resourceName.substring(0, 1).toLowerCase() + resourceName.substring(1);
    }
}
