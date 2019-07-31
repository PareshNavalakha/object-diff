package com.paresh.diff.util;

import com.paresh.diff.dto.Diff;
import com.paresh.diff.dto.DiffResponse;

public class SummaryHelper {

    private static String NEW_LINE = "\r\n";

    public static String getSummary(DiffResponse diffResponse) {

        StringBuilder reponse = new StringBuilder("");
        if (diffResponse != null && !CollectionUtil.isNullOrEmpty(diffResponse.getDiffs())) {
            int addedCount = 0;
            int deleteCount = 0;
            int modifiedCount = 0;
            int unchangedCount = 0;
            for (Diff diff : diffResponse.getDiffs()) {
                switch (diff.getChangeType()) {
                    case ADDED:
                        addedCount++;
                        break;
                    case DELETED:
                        deleteCount++;
                        break;
                    case UPDATED:
                        modifiedCount++;
                        break;
                    case NO_CHANGE:
                        unchangedCount++;
                        break;
                }
            }
            reponse.append("Diff Summary")
                    .append(NEW_LINE)
                    .append("Added count")
                    .append(addedCount)
                    .append(NEW_LINE)
                    .append("Deleted count")
                    .append(deleteCount)
                    .append(NEW_LINE)
                    .append("Modified count")
                    .append(modifiedCount)
                    .append(NEW_LINE)
                    .append("Unmodified count")
                    .append(unchangedCount);
        }
        return reponse.toString();

    }
}
