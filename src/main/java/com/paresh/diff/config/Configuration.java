package com.paresh.diff.config;

import com.paresh.diff.util.Engine;

public interface Configuration {
    void apply(Engine engine);
    void setProperty(String propertyName, Object propertyValue);
    Object getProperty(String propertyName);

}
