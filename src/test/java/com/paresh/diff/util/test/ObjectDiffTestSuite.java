package com.paresh.diff.util.test;


import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        DiffCalculationEngineTest.class,
        ComplexObjectDiffCalculatorTest.class,
        MapDiffCalculatorTest.class,
        ObjectDiffCalculatorTest.class,
        SimpleCollectionDiffCalculatorTest.class,
        ComplexCollectionDiffCalculatorTest.class
})
public class ObjectDiffTestSuite {
}
