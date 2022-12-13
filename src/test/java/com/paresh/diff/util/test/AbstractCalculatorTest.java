package com.paresh.diff.util.test;

import com.paresh.diff.calculators.DiffCalculator;
import com.paresh.diff.calculators.DiffComputeEngine;
import com.paresh.diff.calculators.SimpleCollectionDiffCalculator;
import com.paresh.diff.dto.ChangeType;
import com.paresh.diff.dto.Diff;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;

public abstract class AbstractCalculatorTest {
    protected static DiffComputeEngine diffComputeEngine;
    protected static DiffCalculator diffCalculator;
    protected static Object before;
    protected static Object after;
    protected static Object sameAsBefore;
    protected static Object emptyBefore;
    protected static Object emptyAfter;


    @Test
    public void testBothNullOrEmpty() {
        Collection<Diff> diffs = diffCalculator.apply(emptyBefore, emptyAfter, null);
        Assertions.assertNotNull(diffs, "Response should be non-null");
        Assertions.assertFalse(diffs.isEmpty(), "Response should not be empty");
        Assertions.assertEquals(ChangeType.NO_CHANGE, diffs.iterator().next().getChangeType(), "It should be no change");
    }

    @Test
    public void testNoChange() {
        Collection<Diff> diffs = diffCalculator.apply(before, sameAsBefore, null);
        Assertions.assertNotNull(diffs, "Response should be non-null");
        Assertions.assertFalse(diffs.isEmpty(), "Response should not be empty");
        Assertions.assertEquals(ChangeType.NO_CHANGE, diffs.iterator().next().getChangeType(), "It should be no change");
    }

    @Test
    public void testAdd() {
        Collection<Diff> diffs = diffCalculator.apply(emptyBefore, after, null);
        Assertions.assertNotNull(diffs, "Response should be non-null");
        Assertions.assertFalse(diffs.isEmpty(), "Response should not be empty");
        Assertions.assertEquals(ChangeType.ADDED, diffs.iterator().next().getChangeType(), "It should be addition");
    }

    @Test
    public void testDelete() {
        Collection<Diff> diffs = diffCalculator.apply(before, emptyAfter, null);
        Assertions.assertNotNull(diffs, "Response should be non-null");
        Assertions.assertFalse(diffs.isEmpty(), "Response should not be empty");
        Assertions.assertEquals(ChangeType.DELETED, diffs.iterator().next().getChangeType(), "It should be deletion");
    }

    @Test
    public void testUpdate() {

        Collection<Diff> diffs = diffCalculator.apply(before, after, null);
        Assertions.assertNotNull(diffs, "Response should be non-null");
        Assertions.assertFalse(diffs.isEmpty(), "Response should not be empty");
        if (diffCalculator instanceof SimpleCollectionDiffCalculator) {
            Assertions.assertTrue(diffs.size()>1, "2 responses. One New and Other Deleted.");
        } else {
            Assertions.assertEquals(ChangeType.UPDATED, diffs.iterator().next().getChangeType(), "It should be updated");
        }

    }

}
