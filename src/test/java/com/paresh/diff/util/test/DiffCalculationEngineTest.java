package com.paresh.diff.util.test;

import com.paresh.diff.calculators.DiffComputeEngine;
import com.paresh.diff.dto.ChangeType;
import com.paresh.diff.dto.Diff;
import com.paresh.diff.dto.DiffResponse;
import com.paresh.diff.util.test.TestDataProvider.Address;
import com.paresh.diff.util.test.TestDataProvider.AddressBuilder;
import com.paresh.diff.util.test.TestDataProvider.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class DiffCalculationEngineTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiffCalculationEngineTest.class);
    private static DiffComputeEngine diffComputeEngine;

    @BeforeAll
    public static void setUp() {
        diffComputeEngine = DiffComputeEngine.getInstance();
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
        Assertions.assertNotNull(differences, "Response should not be null");
        Assertions.assertTrue(differences.getDiffs().size() > 0, "Response should contain Collection of diff objects");

        differences.getDiffs().forEach(diff -> {
            Assertions.assertEquals(ChangeType.NO_CHANGE, diff.getChangeType(), "Change type should be No Change");
            Assertions.assertEquals(diff.getBefore(), diff.getAfter(), "Before and after should be same");
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
        Assertions.assertNotNull(differences, "Response should not be null");
        Assertions.assertTrue(differences.getDiffs().size() > 0, "Response should contain Collection of diff objects");

        differences.getDiffs().forEach(diff -> {
            Assertions.assertEquals(ChangeType.UPDATED, diff.getChangeType(), "Change type should be Updated");
            diff.getChildDiffs().stream().filter(childDiff -> childDiff.getFieldDescription().equals("Age")).forEach(childDiff -> {
                Assertions.assertEquals(ChangeType.UPDATED, childDiff.getChangeType(), "Change type should be Updated");
                Assertions.assertEquals(age.toString(), childDiff.getBefore(), "Before Age should be same");
                Assertions.assertEquals(changedAge.toString(), childDiff.getAfter(), "After Age should be same");
            });
        });
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
        Assertions.assertNotNull(differences, "Response should not be null");
        Assertions.assertTrue(differences.getDiffs().size() > 0, "Response should contain Collection of diff objects");
        differences.getDiffs().forEach(diff -> {
            Assertions.assertEquals(ChangeType.ADDED, diff.getChangeType(), "Change type should be Added");
            Assertions.assertNull(diff.getFieldDescription(), "Field description should be null");
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
        Assertions.assertNotNull(differences, "Response should not be null");
        Assertions.assertTrue(differences.getDiffs().size() > 0, "Response should contain Collection of diff objects");
        differences.getDiffs().forEach(diff -> {
            Assertions.assertEquals(ChangeType.DELETED, diff.getChangeType(), "Change type should be Deleted");
            Assertions.assertNull(diff.getFieldDescription(), "Field description should be null");
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
        Assertions.assertNotNull(differences, "Response should not be null");
        Assertions.assertTrue(differences.getDiffs().size() > 0, "Response should contain Collection of diff objects");

        differences.getDiffs().forEach(diff -> {
            Assertions.assertEquals(ChangeType.UPDATED, diff.getChangeType(), "Change type should be Updated");
            diff.getChildDiffs().forEach(childDiff -> {
                switch (childDiff.getFieldDescription()) {
                    case "Age":
                        Assertions.assertEquals(ChangeType.UPDATED, childDiff.getChangeType(), "Change type should be Updated");
                        Assertions.assertEquals(age.toString(), childDiff.getBefore(), "Before Age should be same");
                        Assertions.assertEquals(changedAge.toString(), childDiff.getAfter(), "After Age should be same");
                        break;
                    case "Name":
                        Assertions.assertEquals(ChangeType.NO_CHANGE, childDiff.getChangeType(), "Change type should be No Change");
                        break;
                    case "Attributes":
                        Assertions.assertEquals(ChangeType.NO_CHANGE, childDiff.getChangeType(), "Change type should be No Change");
                        break;
                }
            });
        });
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
