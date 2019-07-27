package com.paresh.diff.util.test;

import com.paresh.diff.dto.ChangeType;
import com.paresh.diff.dto.Diff;
import com.paresh.diff.dto.DiffResponse;
import com.paresh.diff.calculators.DiffComputeEngine;
import com.paresh.diff.util.test.TestDataProvider.Address;
import com.paresh.diff.util.test.TestDataProvider.AddressBuilder;
import com.paresh.diff.util.test.TestDataProvider.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class DiffCalculationEngineTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiffCalculationEngineTest.class);
    private DiffComputeEngine diffComputeEngine;

    @Before
    public void setUp() {
        this.diffComputeEngine = DiffComputeEngine.getInstance();
    }

    @Test
    public void testSameObjects() {
        Integer age = 26;
        String name = "Danniel";
        List<Address> addresses = new ArrayList<>();
        addresses.add(new AddressBuilder().setCity("LA").build());
        addresses.add(new AddressBuilder().setCity("NY").build());

        Map<String, String> attributes = new HashMap<>();
        attributes.put("Height", "180");
        attributes.put("Weight", "95");

        Person dann = com.paresh.diff.util.test.TestDataProvider.getPerson(age, name, addresses, attributes);
        Person sameDann = com.paresh.diff.util.test.TestDataProvider.getPerson(age, name, addresses, attributes);

        DiffResponse differences = diffComputeEngine.findDifferences(dann, sameDann);

        printResults(differences);
        //Assert the response
        Assert.assertTrue("Response should not be null", differences != null);
        Assert.assertTrue("Response should contain Collection of diff objects", differences.getDiffs().size() > 0);

        differences.getDiffs()
                .forEach(diff -> {
                    Assert.assertEquals("Change type should be No Change", ChangeType.NO_CHANGE, diff.getChangeType());
                    Assert.assertEquals("Before and after should be same", diff.getBefore(), diff.getAfter());
                });
    }

    @Test
    public void testChangedObjectWithSimpleAttribute() {
        Integer age = 26;
        String name = "Danniel";
        Integer changedAge = 28;
        List<Address> addresses = new ArrayList<>();
        addresses.add(new AddressBuilder().setCity("LA").build());
        addresses.add(new AddressBuilder().setCity("NY").build());

        Map<String, String> attributes = new HashMap<>();
        attributes.put("Height", "180");
        attributes.put("Weight", "95");

        Person dann = com.paresh.diff.util.test.TestDataProvider.getPerson(age, name, addresses, attributes);
        Person changedDann = com.paresh.diff.util.test.TestDataProvider.getPerson(changedAge, name, addresses, attributes);

        DiffResponse differences = diffComputeEngine.findDifferences(dann, changedDann);

        printResults(differences);
        //Assert the response
        Assert.assertTrue("Response should not be null", differences != null);
        Assert.assertTrue("Response should contain Collection of diff objects", differences.getDiffs().size() > 0);

        differences.getDiffs().stream()
                .forEach(diff -> {
                    Assert.assertEquals("Change type should be Updated", ChangeType.UPDATED, diff.getChangeType());
                    diff.getChildDiffs()
                            .stream()
                            .filter(childDiff -> childDiff.getFieldDescription().equals("Age"))
                            .forEach(childDiff -> {
                                Assert.assertEquals("Change type should be Updated", ChangeType.UPDATED, childDiff.getChangeType());
                                Assert.assertEquals("Before Age should be same", age.toString(), childDiff.getBefore());
                                Assert.assertEquals("After Age should be same", changedAge.toString(), childDiff.getAfter());
                            });
                });
        ;
    }

    @Test
    public void testFirstNullArgument() {
        Integer age = 26;
        String name = "Danniel";
        List<Address> addresses = new ArrayList<>();
        addresses.add(new AddressBuilder().setCity("LA").build());
        addresses.add(new AddressBuilder().setCity("NY").build());

        Map<String, String> attributes = new HashMap<>();
        attributes.put("Height", "180");
        attributes.put("Weight", "95");

        Person dann = com.paresh.diff.util.test.TestDataProvider.getPerson(age, name, addresses, attributes);

        DiffResponse differences = diffComputeEngine.findDifferences(null, dann);

        printResults(differences);
        //Assert the response
        Assert.assertTrue("Response should not be null", differences != null);
        Assert.assertTrue("Response should contain Collection of diff objects", differences.getDiffs().size() > 0);
        differences.getDiffs().forEach(diff -> {
            Assert.assertEquals("Change type should be Added", ChangeType.ADDED, diff.getChangeType());
            Assert.assertEquals("Field description should be null", null, diff.getFieldDescription());
        });
    }

    @Test
    public void testSecondNullArgument() {
        Integer age = 26;
        String name = "Danniel";
        List<Address> addresses = new ArrayList<>();
        addresses.add(new AddressBuilder().setCity("LA").build());
        addresses.add(new AddressBuilder().setCity("NY").build());

        Map<String, String> attributes = new HashMap<>();
        attributes.put("Height", "180");
        attributes.put("Weight", "95");

        Person dann = com.paresh.diff.util.test.TestDataProvider.getPerson(age, name, addresses, attributes);

        DiffResponse differences = diffComputeEngine.findDifferences(dann, null);

        printResults(differences);
        //Assert the response
        Assert.assertTrue("Response should not be null", differences != null);
        Assert.assertTrue("Response should contain Collection of diff objects", differences.getDiffs().size() > 0);
        differences.getDiffs().forEach(diff -> {
            Assert.assertEquals("Change type should be Deleted", ChangeType.DELETED, diff.getChangeType());
            Assert.assertEquals("Field description should be null", null, diff.getFieldDescription());
        });
    }

    @Test
    public void testChangedObjectWithNullComplexAttribute() {
        Integer age = 26;
        String name = "Danniel";
        Integer changedAge = 28;
        List<Address> addresses = new ArrayList<>();
        addresses.add(new AddressBuilder().setCity("LA").build());
        addresses.add(new AddressBuilder().setCity("NY").build());

        Person dann = com.paresh.diff.util.test.TestDataProvider.getPerson(age, name, addresses, null);
        Person changedDann = com.paresh.diff.util.test.TestDataProvider.getPerson(changedAge, name, addresses, null);

        DiffResponse differences = diffComputeEngine.findDifferences(dann, changedDann);

        printResults(differences);
        //Assert the response
        Assert.assertTrue("Response should not be null", differences != null);
        Assert.assertTrue("Response should contain Collection of diff objects", differences.getDiffs().size() > 0);

        differences.getDiffs().stream()
                .forEach(diff -> {
                    Assert.assertEquals("Change type should be Updated", ChangeType.UPDATED, diff.getChangeType());
                    diff.getChildDiffs()
                            .forEach(childDiff -> {
                                if (childDiff.getFieldDescription().equals("Age")) {
                                    Assert.assertEquals("Change type should be Updated", ChangeType.UPDATED, childDiff.getChangeType());
                                    Assert.assertEquals("Before Age should be same", age.toString(), childDiff.getBefore());
                                    Assert.assertEquals("After Age should be same", changedAge.toString(), childDiff.getAfter());
                                } else if (childDiff.getFieldDescription().equals("Name")) {
                                    Assert.assertEquals("Change type should be No Change", ChangeType.NO_CHANGE, childDiff.getChangeType());
                                } else if (childDiff.getFieldDescription().equals("Attributes")) {
                                    Assert.assertEquals("Change type should be No Change", ChangeType.NO_CHANGE, childDiff.getChangeType());
                                }
                            });
                });
        ;
    }

    @Test
    public void testChangedObjects() {
        List<Person> afterTestData = com.paresh.diff.util.test.TestDataProvider.getAfterPersonList();
        List<Person> beforeTestData = com.paresh.diff.util.test.TestDataProvider.getBeforePersonList();
        DiffComputeEngine diffComputeEngine = DiffComputeEngine.getInstance();

        DiffResponse differences = diffComputeEngine.findDifferences(beforeTestData, afterTestData);

        printResults(differences);
    }

    private void printResults(DiffResponse diffResponse) {
        Collection<Diff> onedeltas;
        Collection<Diff> twodeltas;
        Collection<Diff> diffs = diffResponse.getDiffs();
        if (diffs != null) {
            for (Diff diff : diffs) {
                LOGGER.info("Difference: {}", diff);
                onedeltas = diff.getChildDiffs();
                if (onedeltas != null) {
                    for (Diff diffOne : onedeltas) {
                        LOGGER.info(" > " + diffOne);
                        twodeltas = diffOne.getChildDiffs();
                        if (twodeltas != null) {
                            for (Diff diffTwo : twodeltas) {
                                LOGGER.info(" >> " + diffTwo);
                            }
                        }
                    }
                }
            }
        }

    }
}
