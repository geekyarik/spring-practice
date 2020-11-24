package com.ystan.studying.testApp.processors;

import com.ystan.studying.testApp.annotaions.PostProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

public class PostProxyAnnotationContextListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ConfigurableListableBeanFactory factory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContext context = contextRefreshedEvent.getApplicationContext();
        for (String name : context.getBeanDefinitionNames()) {
            try {
                BeanDefinition beanDefinition = factory.getBeanDefinition(name);
                String originalBeanClassName = beanDefinition.getBeanClassName();
                Class<?> originalClass = Class.forName(originalBeanClassName);
                for (Method method : originalClass.getMethods()) {
                    if (method.isAnnotationPresent(PostProxy.class)) {
                        Object bean = context.getBean(name);
                        Method actualMethod = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
                        ReflectionUtils.invokeMethod(actualMethod, bean);
                    }
                }
            } catch (Exception e) {

            }
        }
    }
}
