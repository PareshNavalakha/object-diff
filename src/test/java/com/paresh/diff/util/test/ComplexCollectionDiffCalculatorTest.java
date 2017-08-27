package com.paresh.diff.util.test;

import com.paresh.diff.util.ComplexCollectionDiffCalculator;
import com.paresh.diff.util.DiffComputeEngine;
import org.junit.BeforeClass;

import java.util.ArrayList;
import java.util.List;

public class ComplexCollectionDiffCalculatorTest extends AbstractCalculatorTest {

    @BeforeClass
    public static void setUp() {
        diffComputeEngine = DiffComputeEngine.getInstance();
        diffCalculator = new ComplexCollectionDiffCalculator();
        diffCalculator.registerDeltaCalculationEngine(diffComputeEngine);
        diffComputeEngine.initializeConfiguration();

        before = new ArrayList<TestDataProvider.Person>();
        TestDataProvider.Person beforeEntry = new TestDataProvider.Person();
        beforeEntry.setName("Tom");
        beforeEntry.setAge(20);
        ((List) before).add(beforeEntry);


        after = new ArrayList<TestDataProvider.Person>();
        TestDataProvider.Person afterEntry = new TestDataProvider.Person();
        afterEntry.setName("Tom");
        afterEntry.setAge(21);

        ((List) after).add(afterEntry);

        sameAsBefore = new ArrayList<TestDataProvider.Person>();

        TestDataProvider.Person sameAsBeforeEntry = new TestDataProvider.Person();
        sameAsBeforeEntry.setName("Tom");
        sameAsBeforeEntry.setAge(20);
        ((List) sameAsBefore).add(sameAsBeforeEntry);


        emptyBefore = new ArrayList<TestDataProvider.Person>();
        emptyAfter = new ArrayList<TestDataProvider.Person>();
    }
}
