package com.paresh.diff.util.test;

import com.paresh.diff.dto.ChangeType;
import com.paresh.diff.dto.Diff;
import com.paresh.diff.util.DiffCalculator;
import com.paresh.diff.util.DiffComputeEngine;
import org.hamcrest.core.IsInstanceOf;
import org.hamcrest.core.IsNot;
import org.junit.Assert;
import org.junit.Assume;
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
        Assert.assertEquals("It should be no change",ChangeType.NO_CHANGE,diffs.iterator().next().getChangeType());
    }

    @Test
    public void testNoChange()
    {
        Collection<Diff> diffs = diffCalculator.apply(before, sameAsBefore,null);
        Assert.assertNotNull("Response should be non-null",diffs);
        Assert.assertFalse("Response should not be empty",diffs.isEmpty());
        Assert.assertEquals("It should be no change",ChangeType.NO_CHANGE,diffs.iterator().next().getChangeType());
    }

    @Test
    public void testAdd()
    {
        Collection<Diff> diffs = diffCalculator.apply(emptyBefore, after,null);
        Assert.assertNotNull("Response should be non-null",diffs);
        Assert.assertFalse("Response should not be empty",diffs.isEmpty());
        Assert.assertEquals("It should be addition",ChangeType.ADDED,diffs.iterator().next().getChangeType());
    }

    @Test
    public void testDelete()
    {
        Collection<Diff> diffs = diffCalculator.apply(before, emptyAfter,null);
        Assert.assertNotNull("Response should be non-null",diffs);
        Assert.assertFalse("Response should not be empty",diffs.isEmpty());
        Assert.assertEquals("It should be deletion",ChangeType.DELETED,diffs.iterator().next().getChangeType());
    }

    @Test
    public void testUpdate()
    {
        Assume.assumeThat(before,IsNot.not(IsInstanceOf.instanceOf (Collection.class)));
        Assume.assumeThat(after,IsNot.not(IsInstanceOf.instanceOf (Collection.class)));

        Collection<Diff> diffs = diffCalculator.apply(before, after,null);
        Assert.assertNotNull("Response should be non-null",diffs);
        Assert.assertFalse("Response should not be empty",diffs.isEmpty());
        Assert.assertEquals("It should be updated",ChangeType.UPDATED,diffs.iterator().next().getChangeType());
    }

}
