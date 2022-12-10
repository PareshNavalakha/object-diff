package com.paresh.diff.util.test;

import com.paresh.diff.calculators.DiffComputeEngine;
import com.paresh.diff.calculators.SimpleCollectionDiffCalculator;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.List;

public class SimpleCollectionDiffCalculatorTest extends AbstractCalculatorTest {

    @BeforeAll
    public static void setUp() {
        diffComputeEngine = DiffComputeEngine.getInstance();
        diffCalculator = new SimpleCollectionDiffCalculator();
        diffCalculator.registerDeltaCalculationEngine(diffComputeEngine);
        diffComputeEngine.initializeConfiguration();

        before = new ArrayList<String>();
        ((List) before).add("Entry");
        after = new ArrayList<String>();
        ((List) after).add("Entry1");
        sameAsBefore = new ArrayList<String>();
        ((List) sameAsBefore).add("Entry");
        emptyBefore = new ArrayList<String>();
        emptyAfter = new ArrayList<String>();
    }
}
