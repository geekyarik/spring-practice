package com.ystan.studying.testApp;

import com.ystan.studying.testApp.interfaces.GetRandom;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        GetRandom bean = context.getBean(GetRandom.class);

        int value = bean.getRandomValue();
        System.out.println(value);
    }
}
