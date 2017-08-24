package com.paresh.diff.util.test;

import com.paresh.diff.util.DiffComputeEngine;
import com.paresh.diff.util.SimpleCollectionDiffCalculator;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

public class SimpleCollectionDiffCalculatorTest extends  AbstractCalculatorTest {

    @Before
    public void setUp() {
        diffComputeEngine = DiffComputeEngine.getInstance();
        diffCalculator = new SimpleCollectionDiffCalculator();
        diffCalculator.registerDeltaCalculationEngine(diffComputeEngine);
        diffComputeEngine.initializeConfiguration();

        before = new ArrayList<String>();
        ((List)before).add("Entry");
        after = new ArrayList<String>();
        ((List)after).add("Entry1");
        sameAsBefore= new ArrayList<String>();
        ((List)sameAsBefore).add("Entry");
        emptyBefore=new ArrayList<String>();
        emptyAfter=new ArrayList<String>();
    }
}
