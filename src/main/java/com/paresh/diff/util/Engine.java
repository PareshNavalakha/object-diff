package com.paresh.diff.util;

import com.paresh.diff.config.Configuration;
import com.paresh.diff.dto.Diff;

import java.util.Collection;

public abstract class Engine {

    private Configuration configuration;

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public abstract void registerDeltaCalculator(DiffCalculator deltaCalculator);

    public abstract Collection<Diff> evaluateAndExecute(Object before, Object after, String description);

}
