package com.paresh.diff.util.test;

import com.paresh.diff.calculators.DiffComputeEngine;
import com.paresh.diff.calculators.MapDiffCalculator;
import org.junit.jupiter.api.BeforeAll;

import java.util.HashMap;
import java.util.Map;

public class MapDiffCalculatorTest extends AbstractCalculatorTest {

    @BeforeAll
    public static void setUp() {
        diffComputeEngine = DiffComputeEngine.getInstance();
        diffCalculator = new MapDiffCalculator();
        diffCalculator.registerDeltaCalculationEngine(diffComputeEngine);
        diffComputeEngine.initializeConfiguration();
        before = new HashMap<>();
        ((Map) before).put("Key", "Value");
        after = new HashMap<>();
        ((Map) after).put("Key", "Value1");
        sameAsBefore = new HashMap<>();
        ((Map) sameAsBefore).put("Key", "Value");
        emptyBefore = new HashMap<>();
        emptyAfter = new HashMap<>();
    }
}
