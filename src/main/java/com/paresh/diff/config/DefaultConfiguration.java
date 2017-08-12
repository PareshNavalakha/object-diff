package com.paresh.diff.config;

import com.paresh.diff.util.*;

import java.util.HashMap;
import java.util.Map;

public class DefaultConfiguration implements Configuration {

    private Map<String, Object> properties = new HashMap<>();

    @Override
    public void apply(Engine engine) {
        engine.registerDeltaCalculator(new SimpleCollectionDiffCalculator());
        engine.registerDeltaCalculator(new ComplexCollectionDiffCalculator());
        engine.registerDeltaCalculator(new MapDiffCalculator());
        engine.registerDeltaCalculator(new ComplexObjectDiffCalculator());
        engine.registerDeltaCalculator(new ObjectDiffCalculator());
    }

    @Override
    public void setProperty(String propertyName, Object propertyValue)
    {
        properties.put(propertyName,propertyValue);
    }

    @Override
    public Object getProperty(String propertyName)
    {
        return properties.get(propertyName);
    }
}
