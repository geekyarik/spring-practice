package com.spring.ystan.processor;

import com.spring.ystan.annotation.DataExportAttribute;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Component
public class ClassExportPreProcessor {

    public static final String FIELD_RESOURCE_BUNDLE = "fields";
    public static final String FIELD_RESOURCE_KEY = "field.";

    private ResourceBundle getResourceBundle(Locale locale) { ;
        return ResourceBundle.getBundle(FIELD_RESOURCE_BUNDLE, locale);
    }

    public Map<String, Field> getFields(Class dataClass, Locale locale) {
        Map<String, Field> fields = new HashMap<>();
        for (Field field : dataClass.getDeclaredFields()) {
            DataExportAttribute annotation = field.getAnnotation(DataExportAttribute.class);
            if (annotation != null) {
                String name = annotation.name();
                String localedName = getResourceBundle(locale).getString(FIELD_RESOURCE_KEY + name);
                fields.put(localedName, field);
            }
        }

        return fields;
    }
}
