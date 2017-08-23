package com.paresh.diff.util.test;

import com.paresh.diff.util.ComplexCollectionDiffCalculator;
import com.paresh.diff.util.SimpleCollectionDiffCalculator;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

public class ComplexCollectionDiffCalculatorTest extends  AbstractCalculatorTest {

    @Before
    public void setUp() {
        super.setUp();
        diffCalculator = new ComplexCollectionDiffCalculator();
        diffCalculator.registerDeltaCalculationEngine(diffComputeEngine);
        diffComputeEngine.initializeConfiguration();

        before = new ArrayList<TestDataProvider.Person>();
        TestDataProvider.Person beforeEntry = new TestDataProvider.Person();
        beforeEntry.setName("Tom");

        ((List)before).add(beforeEntry);


        after = new ArrayList<TestDataProvider.Person>();
        TestDataProvider.Person afterEntry = new TestDataProvider.Person();
        afterEntry.setName("Samantha");
        ((List)after).add(afterEntry);

        sameAsBefore= new ArrayList<TestDataProvider.Person>();

        TestDataProvider.Person sameAsBeforeEntry = new TestDataProvider.Person();
        sameAsBeforeEntry.setName("Tom");
        ((List)sameAsBefore).add(sameAsBeforeEntry);


        emptyBefore=new ArrayList<TestDataProvider.Person>();
        emptyAfter=new ArrayList<TestDataProvider.Person>();
    }
}
