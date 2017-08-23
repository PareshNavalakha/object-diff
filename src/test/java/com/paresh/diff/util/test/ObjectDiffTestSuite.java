package com.paresh.diff.util.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DiffCalculationEngineTest.class,
        ComplexObjectDiffCalculatorTest.class,
        MapDiffCalculatorTest.class,
        ObjectDiffCalculatorTest.class,
        SimpleCollectionDiffCalculatorTest.class,
        ComplexCollectionDiffCalculatorTest.class
})
public class ObjectDiffTestSuite {
}
