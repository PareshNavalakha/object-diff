package com.paresh.diff.util.test;

import com.paresh.diff.calculators.ComplexObjectDiffCalculator;
import com.paresh.diff.calculators.DiffComputeEngine;
import org.junit.BeforeClass;

public class ComplexObjectDiffCalculatorTest extends AbstractCalculatorTest {

    @BeforeClass
    public static void setUp() {
        diffComputeEngine = DiffComputeEngine.getInstance();
        diffCalculator = new ComplexObjectDiffCalculator();
        diffCalculator.registerDeltaCalculationEngine(diffComputeEngine);
        diffComputeEngine.initializeConfiguration();


        before = new TestDataProvider.Person();
        ((TestDataProvider.Person) before).setName("Tim");
        ((TestDataProvider.Person) before).setAge(20);

        after = new TestDataProvider.Person();
        ((TestDataProvider.Person) after).setName("Tim");
        ((TestDataProvider.Person) after).setAge(21);

        sameAsBefore = new TestDataProvider.Person();
        ((TestDataProvider.Person) sameAsBefore).setName("Tim");
        ((TestDataProvider.Person) sameAsBefore).setAge(20);

        emptyBefore = null;
        emptyAfter = null;
    }

}
