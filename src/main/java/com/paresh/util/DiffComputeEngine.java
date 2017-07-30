package com.paresh.util;

import com.paresh.cache.ClassMetadataCache;
import com.paresh.dto.Diff;
import com.paresh.exception.BothAreNullException;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Admin on 02-07-2017.
 */
public class DiffComputeEngine {
    private static final DiffComputeEngine ourInstance = new DiffComputeEngine();
    private final List<DiffCalculator> calculators = new LinkedList<>();

    //Below registration of Generic Diff Calculators needs to move out.
    //To be decided precisely where. Do not expect library users to explicitly configure prior to using the functions
    {
        this.registerDeltaCalculator(new CollectionDiffCalculator());
        this.registerDeltaCalculator(new MapDiffCalculator());
        this.registerDeltaCalculator(new ComplexObjectDiffCalculator());
        this.registerDeltaCalculator(new ObjectDiffCalculator());


    }

    private DiffComputeEngine() {
    }

    public static DiffComputeEngine getInstance() {
        return ourInstance;
    }

    private void registerDeltaCalculator(DiffCalculator deltaCalculator) {
        calculators.add(deltaCalculator);
        deltaCalculator.registerDeltaCalculationEngine(this);
        calculators.sort((DiffCalculator o1, DiffCalculator o2) -> o2.getOrder() - o1.getOrder());

    }

    public List<Diff> findDifferences(Object before, Object after) {
        List<Diff> returnValue = evaluateAndExecute(before, after, null);
        ClassMetadataCache.getInstance().clearCache();
        return returnValue;
    }

    List<Diff> evaluateAndExecute(Object before, Object after, String description) {
        for (DiffCalculator calculator : calculators) {
            try {

                if (calculator.test(getNonNull(before, after))) {
                    return calculator.apply(before, after, description);
                }
            } catch (BothAreNullException e) {
                return Collections.singletonList(new Diff.Builder().hasNotChanged().build());
            }
        }

        System.out.println("Could not find relevant Calculator for " + before + " " + after + " " + description);
        return null;
    }

    private Object getNonNull(Object before, Object after) throws BothAreNullException {
        if (before != null) {
            return before;
        }
        if (after != null) {
            return after;
        }
        throw new BothAreNullException();
    }
}
