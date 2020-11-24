package com.ystan.studying.testApp.beans;

import com.ystan.studying.testApp.annotaions.PostProxy;
import com.ystan.studying.testApp.interfaces.GetRandom;

public class AdvancedRandomHolder extends RandomHolder implements GetRandom {

    @Override
    public int getValue() {
        System.out.println("Advanced operation");
        return super.getValue();
    }

    @PostProxy
    @Override
    public void afterBuiltContext() {
        System.out.println("Advanced operation:");
        super.afterBuiltContext();
    }

    @Override
    public void init() {
        System.out.println("Advanced operation");
        super.init();
    }
}
