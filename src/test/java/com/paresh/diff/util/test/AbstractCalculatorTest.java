package com.paresh.diff.util.test;

import com.paresh.diff.dto.ChangeType;
import com.paresh.diff.dto.Diff;
import com.paresh.diff.util.DiffCalculator;
import com.paresh.diff.util.DiffComputeEngine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

public class AbstractCalculatorTest {
    protected DiffComputeEngine diffComputeEngine;
    protected DiffCalculator diffCalculator;
    protected Object before;
    protected Object after;
    protected Object sameAsBefore;
    protected Object emptyBefore;
    protected Object emptyAfter;
    @Before
    public void setUp() {
        diffComputeEngine = DiffComputeEngine.getInstance();
    }

    @Test
    public void testBothNullOrEmpty()
    {
        Collection<Diff> diffs = diffCalculator.apply(emptyBefore, emptyAfter,null);
        Assert.assertNotNull("Response should be non-null",diffs);
        Assert.assertFalse("Response should not be empty",diffs.isEmpty());
        Assert.assertEquals("It should be no change",diffs.iterator().next().getChangeType(), ChangeType.NO_CHANGE);
    }

    @Test
    public void testNoChange()
    {
        Collection<Diff> diffs = diffCalculator.apply(before, sameAsBefore,null);
        Assert.assertNotNull("Response should be non-null",diffs);
        Assert.assertFalse("Response should not be empty",diffs.isEmpty());
        Assert.assertEquals("It should be no change",diffs.iterator().next().getChangeType(), ChangeType.NO_CHANGE);
    }

    @Test
    public void testAdd()
    {
        Collection<Diff> diffs = diffCalculator.apply(emptyBefore, after,null);
        Assert.assertNotNull("Response should be non-null",diffs);
        Assert.assertFalse("Response should not be empty",diffs.isEmpty());
        Assert.assertEquals("It should be addition",diffs.iterator().next().getChangeType(), ChangeType.ADDED);
    }

    @Test
    public void testDelete()
    {
        Collection<Diff> diffs = diffCalculator.apply(before, emptyAfter,null);
        Assert.assertNotNull("Response should be non-null",diffs);
        Assert.assertFalse("Response should not be empty",diffs.isEmpty());
        Assert.assertEquals("It should be deletion",diffs.iterator().next().getChangeType(), ChangeType.DELETED);
    }

    @Test
    public void testUpdate()
    {
        Collection<Diff> diffs = diffCalculator.apply(before, after,null);
        Assert.assertNotNull("Response should be non-null",diffs);
        Assert.assertFalse("Response should not be empty",diffs.isEmpty());
        Assert.assertEquals("It should be updated",diffs.iterator().next().getChangeType(), ChangeType.UPDATED);
    }

}
