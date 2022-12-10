package com.paresh.diff.util.test;

import com.paresh.diff.calculators.ComplexCollectionDiffCalculator;
import com.paresh.diff.calculators.DiffComputeEngine;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.List;

public class ComplexCollectionDiffCalculatorTest extends AbstractCalculatorTest {

    @BeforeAll
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

        TestDataProvider.Person beforeEntry2 = new TestDataProvider.Person();
        beforeEntry2.setName("Mike");
        beforeEntry2.setAge(22);
        ((List) before).add(beforeEntry2);


        after = new ArrayList<TestDataProvider.Person>();
        TestDataProvider.Person afterEntry = new TestDataProvider.Person();
        afterEntry.setName("Tom");
        afterEntry.setAge(21);
        ((List) after).add(afterEntry);

        TestDataProvider.Person afterEntry2 = new TestDataProvider.Person();
        afterEntry2.setName("Mike");
        afterEntry2.setAge(23);
        ((List) after).add(afterEntry2);

        sameAsBefore = new ArrayList<TestDataProvider.Person>();

        TestDataProvider.Person sameAsBeforeEntry = new TestDataProvider.Person();
        sameAsBeforeEntry.setName("Tom");
        sameAsBeforeEntry.setAge(20);
        ((List) sameAsBefore).add(sameAsBeforeEntry);

        TestDataProvider.Person sameAsBeforeEntry2 = new TestDataProvider.Person();
        sameAsBeforeEntry2.setName("Mike");
        sameAsBeforeEntry2.setAge(22);
        ((List) sameAsBefore).add(sameAsBeforeEntry2);


        emptyBefore = new ArrayList<TestDataProvider.Person>();
        emptyAfter = new ArrayList<TestDataProvider.Person>();
    }
}
