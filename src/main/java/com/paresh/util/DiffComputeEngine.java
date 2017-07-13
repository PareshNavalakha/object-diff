package com.paresh.util;

import com.paresh.dto.Diff;
import com.paresh.dto.DiffBuilder;
import com.paresh.exception.BothAreNullException;

import java.util.*;

/**
 * Created by Admin on 02-07-2017.
 */
public class DiffComputeEngine {
    private static final DiffComputeEngine ourInstance = new DiffComputeEngine();

    public static DiffComputeEngine getInstance() {
        return ourInstance;
    }

    private DiffComputeEngine() {
    }
    private final List<DiffCalculator> calculators = new LinkedList<>();

    private void registerDeltaCalculator(DiffCalculator deltaCalculator) {
        calculators.add(deltaCalculator);
        deltaCalculator.registerDeltaCalculationEngine(this);
        calculators.sort((DiffCalculator o1, DiffCalculator o2) -> o2.getOrder() - o1.getOrder());

    }

    //Below registration of Generic Diff Calculators needs to move out.
    //To be decided precisely where. Do not expect library users to explicitly configure prior to using the functions
    {
        this.registerDeltaCalculator(new CollectionDiffCalculator());
        this.registerDeltaCalculator(new MapDiffCalculator());
        this.registerDeltaCalculator(new ComplexObjectDiffCalculator());
        this.registerDeltaCalculator(new ObjectDiffCalculator());


    }

    public List<Diff> findDifferences(Object before, Object after) {
       return evaluateAndExecute(before, after, null);
    }

    List<Diff> evaluateAndExecute(Object before, Object after, String description) {
        for (DiffCalculator calculator : calculators) {
            try {

                if (calculator.test(getNonNull(before, after))) {
                    return calculator.apply(before, after, description);
                }
            } catch (BothAreNullException e) {
                return Collections.singletonList(new DiffBuilder().hasNotChanged().build());
            }
        }

        System.out.println("Could not find relevant Calculator for " + before +" "+after+" "+description);
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
