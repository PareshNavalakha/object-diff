package com.paresh.util;

import com.paresh.dto.Diff;
import com.paresh.dto.DiffBuilder;
import com.paresh.exception.BothAreNullException;

import java.util.*;

/**
 * Created by Admin on 02-07-2017.
 */
public class DiffComputeEngine {
    private static DiffComputeEngine ourInstance = new DiffComputeEngine();

    public static DiffComputeEngine getInstance() {
        return ourInstance;
    }

    private DiffComputeEngine() {
    }
    private List<DiffCalculator> calculators = new ArrayList<DiffCalculator>();

    public void registerDeltaCalculator(DiffCalculator deltaCalculator) {
        calculators.add(deltaCalculator);
        deltaCalculator.registerDeltaCalculationEngine(this);
        Collections.sort(calculators,(DiffCalculator o1, DiffCalculator o2) -> o2.getPriority()-o1.getPriority() );

    }

    public List<Diff> calculateDelta(Object before, Object after) {
        return null;
    }


    //Below registration of Generic Diff Calculators needs to move out.
    //To be decided precisely where. Do not expect library users to explicitly configure prior to using the functions
    {
        this.registerDeltaCalculator(new CollectionDiffCalculator());
        this.registerDeltaCalculator(new MapDiffCalculator());
        this.registerDeltaCalculator(new ComplexObjectDiffCalculator());
        this.registerDeltaCalculator(new ObjectDiffCalculator());


    }

    public List<Diff> evaluateAndExecute(Object before, Object after, String description) {
        for (DiffCalculator calculator : calculators) {
            try {

                if (calculator.test(getNonNull(before, after))) {
                    return calculator.apply(before, after, description);
                }
            } catch (BothAreNullException e) {
                return Arrays.asList(new DiffBuilder().hasNotChanged().build());
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
