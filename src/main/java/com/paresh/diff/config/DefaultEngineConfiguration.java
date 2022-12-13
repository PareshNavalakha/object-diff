package com.paresh.diff.config;

import com.paresh.diff.calculators.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultEngineConfiguration implements EngineConfiguration {

    private Map<String, Object> properties = new ConcurrentHashMap<>();

    @Override
    public void apply(Engine engine) {
        engine.registerDeltaCalculator(new SimpleCollectionDiffCalculator());
        engine.registerDeltaCalculator(new ComplexCollectionDiffCalculator());
        engine.registerDeltaCalculator(new MapDiffCalculator());
        engine.registerDeltaCalculator(new ComplexObjectDiffCalculator());
        engine.registerDeltaCalculator(new ObjectDiffCalculator());
    }

    @Override
    public void setProperty(String propertyName, Object propertyValue) {
        properties.put(propertyName, propertyValue);
    }

    @Override
    public Object getProperty(String propertyName) {
        return properties.get(propertyName);
    }
}
