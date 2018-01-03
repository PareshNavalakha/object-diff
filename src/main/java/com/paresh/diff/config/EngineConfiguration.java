package com.paresh.diff.config;

import com.paresh.diff.util.Engine;

import java.lang.reflect.Method;

public interface EngineConfiguration {
    void apply(Engine engine);

    void setProperty(String propertyName, Object propertyValue);

    Object getProperty(String propertyName);
}
