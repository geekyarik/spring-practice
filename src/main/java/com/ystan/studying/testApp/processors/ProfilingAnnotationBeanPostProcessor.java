package com.ystan.studying.testApp.processors;

import com.ystan.studying.testApp.annotaions.Profiling;
import com.ystan.studying.testApp.annotaions.controller.ProfilingController;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ProfilingAnnotationBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class> beans = new HashMap<>();
    private ProfilingController profilingController = new ProfilingController();

    public ProfilingAnnotationBeanPostProcessor() throws Exception {
        MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
        beanServer.registerMBean(profilingController, new ObjectName("profiling", "name", "controller"));
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> bClass = bean.getClass();
        Profiling annotation = bClass.getAnnotation(Profiling.class);
        if (annotation != null) {
            beans.put(beanName, bClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = beans.get(beanName);
        if (beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                    if (profilingController.isEnabled()) {
                        System.out.println("Profiling of method ." + method.getName());
                        long start = System.currentTimeMillis();
                        Object returnVal = method.invoke(bean, objects);
                        System.out.println("Took milliseconds " + System.currentTimeMillis());
                        return returnVal;
                    } else {
                        return method.invoke(bean, objects);
                    }
                }
            });
        }

        return bean;
    }
}
