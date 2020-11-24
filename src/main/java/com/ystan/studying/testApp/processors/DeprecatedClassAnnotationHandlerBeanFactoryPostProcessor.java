package com.ystan.studying.testApp.processors;

import com.ystan.studying.testApp.annotaions.DeprecatedClass;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class DeprecatedClassAnnotationHandlerBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @SneakyThrows
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        for (String definitionName : configurableListableBeanFactory.getBeanDefinitionNames()) {
            BeanDefinition definition = configurableListableBeanFactory.getBeanDefinition(definitionName);
            Class<?> definitionClass = Class.forName(definition.getBeanClassName());
            DeprecatedClass annotation = definitionClass.getAnnotation(DeprecatedClass.class);
            if (annotation != null) {
                definition.setBeanClassName(annotation.newClass().getName());
            }
        }
    }
}
