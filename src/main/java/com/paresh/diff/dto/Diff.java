package com.paresh.diff.dto;

import com.paresh.diff.cache.ClassMetadataCache;
import com.paresh.diff.constants.Constants;

import java.util.Collection;

/**
 * Created by Admin on 27-06-2017.
 */
public class Diff {
    private ChangeType changeType;
    private String fieldDescription;
    private String before;
    private String after;
    private String identifier;
    private Collection<Diff> childDiffs;

    public Diff(ChangeType changeType, String fieldDescription, String before, String after, String identifier) {
        this.changeType = changeType;
        this.fieldDescription = fieldDescription;
        this.before = before;
        this.after = after;
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }

    public String getBefore() {
        return before;
    }

    public String getAfter() {
        return after;
    }

    public Collection<Diff> getChildDiffs() {
        return childDiffs;
    }

    public void setChildDiffs(Collection<Diff> childDiffs) {
        this.childDiffs = childDiffs;
    }

    @Override
    public String toString() {
        return identifier != null && !identifier.isEmpty() ? "{" +
                "changeType=" + changeType +
                ", fieldDescription=" + fieldDescription + " " +
                ", before=" + before +
                ", after=" + after +
                ", identifier=" + identifier +
                '}' : "changeType=" + changeType +
                ", fieldDescription=" + fieldDescription + " " +
                ", before=" + before +
                ", after=" + after +
                '}';
    }

    public static class Builder {

        private ChangeType changeType;
        private String fieldDescription;
        private Object before;
        private Object after;
        private String identifier;

        public Builder isAdded() {
            this.changeType = ChangeType.ADDED;
            return this;
        }


        public Builder isDeleted() {
            this.changeType = ChangeType.DELETED;
            return this;
        }

        public Builder isUpdated() {
            this.changeType = ChangeType.UPDATED;
            return this;
        }

        public Builder hasNotChanged() {
            this.changeType = ChangeType.NO_CHANGE;
            return this;
        }

        public Builder setBeforeValue(Object before) {
            this.before = before;
            return this;
        }

        public Builder setAfterValue(Object after) {
            this.after = after;
            return this;
        }

        public Builder setFieldDescription(String fieldDescription) {
            this.fieldDescription = fieldDescription;
            return this;
        }

        public Diff build() {

            if (changeType != null) {
                switch (changeType) {
                    case DELETED:
                    case UPDATED:
                    case NO_CHANGE:
                        identifier = ClassMetadataCache.getInstance().getIdentifierString(before);
                        break;
                    case ADDED:
                        identifier = ClassMetadataCache.getInstance().getIdentifierString(after);
                        break;
                }
            }

            return new Diff(changeType, fieldDescription, before == null ? Constants.BLANK : before.toString(), after == null ? Constants.BLANK : after.toString(), identifier);
        }

    }

}
