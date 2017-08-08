package com.paresh.util;

import com.paresh.cache.ClassMetadataCache;
import com.paresh.dto.Diff;
import com.paresh.exception.BothAreNullException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Admin on 02-07-2017.
 */
public class DiffComputeEngine {
    private static final DiffComputeEngine ourInstance = new DiffComputeEngine();
    private final List<DiffCalculator> calculators = new LinkedList<>();
    private final Logger logger = LoggerFactory.getLogger(getClass());


    private DiffComputeEngine() {
    }

    public static DiffComputeEngine getInstance() {
        return ourInstance;
    }


    //Below registration of Generic Diff Calculators needs to move out.
    //To be decided precisely where. Do not expect library users to explicitly configure prior to using the functions
    {
        this.registerDeltaCalculator(new CollectionDiffCalculator());
        this.registerDeltaCalculator(new MapDiffCalculator());
        this.registerDeltaCalculator(new ComplexObjectDiffCalculator());
        this.registerDeltaCalculator(new ObjectDiffCalculator());
    }


    private void registerDeltaCalculator(DiffCalculator deltaCalculator) {
        calculators.add(deltaCalculator);
        deltaCalculator.registerDeltaCalculationEngine(this);
        calculators.sort((DiffCalculator o1, DiffCalculator o2) -> o2.getOrder() - o1.getOrder());

    }

    public Collection<Diff> findDifferences(Object before, Object after) {
        Collection<Diff> returnValue = evaluateAndExecute(before, after, null);
        ClassMetadataCache.getInstance().clearCache();
        return returnValue;
    }

    Collection<Diff> evaluateAndExecute(Object before, Object after, String description) {
        for (DiffCalculator calculator : calculators) {
            try {

                if (calculator.test(getNonNull(before, after))) {
                    return calculator.apply(before, after, description);
                }
            } catch (BothAreNullException e) {
                return Collections.singletonList(new Diff.Builder().hasNotChanged().build());
            }
        }

        logger.error("Could not find relevant Calculator for {} {} {}" , before , after , description);
        return new ArrayList<>();
    }

    private Object getNonNull(Object before, Object after) throws BothAreNullException {
        if (before != null) {
            return before;
        }
        if (after != null) {
            return after;
        }
        throw new BothAreNullException();
    }
}
