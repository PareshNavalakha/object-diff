package com.paresh.util;

import com.paresh.dto.Diff;
import com.paresh.dto.DiffBuilder;

import java.util.LinkedList;
import java.util.List;


public class ObjectDiffCalculator extends DiffCalculator {

    @Override
    public List<Diff> apply(Object before, Object after, String description) {
        List<Diff> diffs = new LinkedList<>();
        if (before == null && after != null) {
            diffs.add(new DiffBuilder().isAdded().setAfterValue(after).setFieldDescription(description).build());
        } else if (before != null && after == null) {
            diffs.add(new DiffBuilder().isDeleted().setBeforeValue(before).setFieldDescription(description).build());
        } else {
            if (before!=null && before.equals(after)) {
                diffs.add(new DiffBuilder().hasNotChanged().setBeforeValue(before).setAfterValue(after).setFieldDescription(description).build());
            } else {
                diffs.add(new DiffBuilder().isUpdated().setBeforeValue(before).setAfterValue(after).setFieldDescription(description).build());
            }
        }
        return diffs;
    }


    //Default fallback in case no other calculator gets triggered
    @Override
    public int getOrder() {
        return 0;
    }


    @Override
    public boolean test(Object o) {
        return true;
    }

}