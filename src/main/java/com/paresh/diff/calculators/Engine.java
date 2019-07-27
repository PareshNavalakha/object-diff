package com.paresh.diff.calculators;

import com.paresh.diff.config.ClassMetaDataConfiguration;
import com.paresh.diff.config.EngineConfiguration;
import com.paresh.diff.dto.Diff;

import java.util.Collection;

public abstract class Engine {

    private EngineConfiguration engineConfiguration;
    private ClassMetaDataConfiguration classMetaDataConfiguration;

    public EngineConfiguration getEngineConfiguration() {
        return engineConfiguration;
    }

    public void setEngineConfiguration(EngineConfiguration engineConfiguration) {
        this.engineConfiguration = engineConfiguration;
    }

    public ClassMetaDataConfiguration getClassMetaDataConfiguration() {
        return classMetaDataConfiguration;
    }

    public void setClassMetaDataConfiguration(ClassMetaDataConfiguration classMetaDataConfiguration) {
        this.classMetaDataConfiguration = classMetaDataConfiguration;
    }

    public abstract void registerDeltaCalculator(DiffCalculator deltaCalculator);

    public abstract Collection<Diff> evaluateAndExecute(Object before, Object after, String description);

}
