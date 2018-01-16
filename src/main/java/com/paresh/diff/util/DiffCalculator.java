package com.paresh.diff.util;

import java.util.function.BiPredicate;

/**
 * Interface to be implemented by all Diff Calculators
 * DiffComputeEngine registration is provided for callbacks if required.
 * for eg. if an Object Diff Calculator comes across a Collection or another Complex object
 * Default Calculators will have 0 as priority. This allows for others to extend base functionality
 */
public abstract class DiffCalculator implements DiffFunction, BiPredicate<Object, Object> {

    private Engine engine;

    public abstract int getOrder();

    public void registerDeltaCalculationEngine(Engine engine) {
        this.engine = engine;
    }

    Engine getDiffComputeEngine() {
        return engine;
    }
}
