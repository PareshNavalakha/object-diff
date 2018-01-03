package com.paresh.diff.util;

import com.paresh.diff.cache.ClassMetadataCache;
import com.paresh.diff.config.ClassMetaDataConfiguration;
import com.paresh.diff.config.DefaultEngineConfiguration;
import com.paresh.diff.config.EngineConfiguration;
import com.paresh.diff.config.reflection.ReflectionConfiguration;
import com.paresh.diff.dto.Diff;
import com.paresh.diff.dto.DiffResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Admin on 02-07-2017.
 */
public class DiffComputeEngine extends Engine {
    private static final DiffComputeEngine ourInstance = new DiffComputeEngine();
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<DiffCalculator> calculators = new LinkedList<>();

    private DiffComputeEngine() {
    }

    public static DiffComputeEngine getInstance() {
        return ourInstance;
    }

    @Override
    public void registerDeltaCalculator(DiffCalculator deltaCalculator) {
        calculators.add(deltaCalculator);
        deltaCalculator.registerDeltaCalculationEngine(this);
        calculators.sort((DiffCalculator o1, DiffCalculator o2) -> o2.getOrder() - o1.getOrder());
    }

    @Override
    public Collection<Diff> evaluateAndExecute(Object before, Object after, String description) {
        for (DiffCalculator calculator : calculators) {
            if (calculator.test(before, after)) {
                return calculator.apply(before, after, description);
            }
        }
        logger.error("Could not find relevant Calculator for {} {} {}", before, after, description);
        return new ArrayList<>();
    }

    //Initialize configuration if not done already
    public void initializeConfiguration() {
        EngineConfiguration engineConfiguration = getEngineConfiguration();
        if (engineConfiguration == null) {
            engineConfiguration = new DefaultEngineConfiguration();
            setEngineConfiguration(engineConfiguration);
            engineConfiguration.apply(this);
        }

        ClassMetaDataConfiguration classMetaDataConfiguration = getClassMetaDataConfiguration();
        if (classMetaDataConfiguration == null) {
            classMetaDataConfiguration = new ReflectionConfiguration();
            setClassMetaDataConfiguration(classMetaDataConfiguration);
        }
    }

    public DiffResponse findDifferences(Object before, Object after) {
        DiffResponse diffResponse = new DiffResponse();
        initializeConfiguration();
        Collection<Diff> returnValue = evaluateAndExecute(before, after, null);
        diffResponse.setDiffs(returnValue);
        diffResponse.setClassMetaDataMap(ClassMetadataCache.getInstance().getClassMetaDataMap());
        return diffResponse;
    }

}
