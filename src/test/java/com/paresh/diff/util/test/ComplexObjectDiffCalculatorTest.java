package com.paresh.diff.util.test;

import com.paresh.diff.util.ComplexObjectDiffCalculator;
import com.paresh.diff.util.MapDiffCalculator;
import org.junit.Before;

import java.util.HashMap;
import java.util.Map;

public class ComplexObjectDiffCalculatorTest extends  AbstractCalculatorTest {

    @Before
    public void setUp() {
        super.setUp();
        diffCalculator = new ComplexObjectDiffCalculator();
        diffCalculator.registerDeltaCalculationEngine(diffComputeEngine);
        diffComputeEngine.initializeConfiguration();


        before = new TestDataProvider.Person();
        ((TestDataProvider.Person)before).setName("Tim");
        ((TestDataProvider.Person)before).setAge(20);

        after =new TestDataProvider.Person();
        ((TestDataProvider.Person)after).setName("Tim");
        ((TestDataProvider.Person)after).setAge(21);

        sameAsBefore= new TestDataProvider.Person();
        ((TestDataProvider.Person)sameAsBefore).setName("Tim");
        ((TestDataProvider.Person)sameAsBefore).setAge(20);

        emptyBefore=null;
        emptyAfter=null;
    }

}
