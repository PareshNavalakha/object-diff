package com.paresh.util;

import java.util.function.Predicate;

/**
 * Interface to be implemented by all Diff Calculators
 * DiffComputeEngine registration is provided for callbacks if required.
 * for eg. if an Object Diff Calculator comes across a Collection or another Complex object
 * Default Calculators will have 0 as priority. This allows for others to extend base functionality
 */
public abstract class DiffCalculator implements DiffFunction, Predicate<Object> {

    private DiffComputeEngine diffComputeEngine;

    public abstract int getPriority();

    public void registerDeltaCalculationEngine(DiffComputeEngine diffComputeEngine) {
        this.diffComputeEngine = diffComputeEngine;
    }

    public DiffComputeEngine getDiffComputeEngine() {
        return diffComputeEngine;
    }
}
