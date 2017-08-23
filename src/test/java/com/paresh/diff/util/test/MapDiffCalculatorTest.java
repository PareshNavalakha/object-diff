package com.paresh.diff.util.test;

import com.paresh.diff.util.MapDiffCalculator;
import com.paresh.diff.util.ObjectDiffCalculator;
import org.junit.Before;

import java.util.HashMap;
import java.util.Map;

public class MapDiffCalculatorTest extends  AbstractCalculatorTest {

    @Before
    public void setUp() {
        super.setUp();
        diffCalculator = new MapDiffCalculator();
        diffCalculator.registerDeltaCalculationEngine(diffComputeEngine);
        diffComputeEngine.initializeConfiguration();
        before = new HashMap<>();
        ((Map)before).put("Key","Value");
        after = new HashMap<>();
        ((Map)after).put("Key","Value1");
        sameAsBefore= new HashMap<>();
        ((Map)sameAsBefore).put("Key","Value");
        emptyBefore=new HashMap<>();
        emptyAfter=new HashMap<>();
    }
}
