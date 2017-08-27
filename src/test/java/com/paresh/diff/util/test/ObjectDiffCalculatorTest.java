package com.paresh.diff.util.test;

import com.paresh.diff.util.DiffComputeEngine;
import com.paresh.diff.util.ObjectDiffCalculator;
import org.junit.BeforeClass;

public class ObjectDiffCalculatorTest extends AbstractCalculatorTest {

    @BeforeClass
    public static void setUp() {
        diffComputeEngine = DiffComputeEngine.getInstance();
        diffCalculator = new ObjectDiffCalculator();
        diffCalculator.registerDeltaCalculationEngine(diffComputeEngine);
        diffComputeEngine.initializeConfiguration();

        before = "Some text";
        after = "Some other text";
        sameAsBefore = "Some text";
        emptyBefore = null;
        emptyAfter = null;
    }
}
