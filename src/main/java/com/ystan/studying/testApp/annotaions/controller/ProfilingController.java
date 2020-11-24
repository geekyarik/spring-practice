package com.ystan.studying.testApp.annotaions.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfilingController implements ProfilingControllerMBean {
    private boolean enabled;
}
