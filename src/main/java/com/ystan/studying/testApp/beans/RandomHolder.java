package com.ystan.studying.testApp.beans;

import com.ystan.studying.testApp.annotaions.DeprecatedClass;
import com.ystan.studying.testApp.annotaions.InjectRandomInt;
import com.ystan.studying.testApp.annotaions.PostProxy;
import com.ystan.studying.testApp.annotaions.Profiling;
import com.ystan.studying.testApp.interfaces.GetRandom;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;

@Setter
@Getter
@Profiling
@DeprecatedClass(newClass = AdvancedRandomHolder.class)
public class RandomHolder implements GetRandom {

    @Getter
    @InjectRandomInt(min = 30, max = 50)
    private int value;


    public RandomHolder() {
        System.out.println("Phase 1: constructor");
    }

    @PostConstruct
    public void init() {
        System.out.println("Phase 2: init method");
    }

    @PostProxy
    public void afterBuiltContext() {
        System.out.println("Phase 3: after context is built");
    }

    @Override
    public int getRandomValue() {
        return value;
    }
}
