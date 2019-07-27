package com.paresh.diff.config;

import com.paresh.diff.calculators.Engine;

public interface EngineConfiguration {
    void apply(Engine engine);

    void setProperty(String propertyName, Object propertyValue);

    Object getProperty(String propertyName);
}
