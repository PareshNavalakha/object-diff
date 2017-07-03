package com.paresh.dto;


import com.paresh.cache.ClassMetadataCache;

/**
 * Created by Admin on 27-06-2017.
 */
public class DiffBuilder {

    private ChangeType changeType;
    private String fieldDescription;
    private Object before;
    private Object after;
    private String identifier;

    public DiffBuilder isAdded() {
        this.changeType = ChangeType.ADDED;
        return this;
    }


    public DiffBuilder isDeleted() {
        this.changeType = ChangeType.DELETED;
        return this;
    }

    public DiffBuilder isUpdated() {
        this.changeType = ChangeType.UPDATED;
        return this;
    }

    public DiffBuilder hasNotChanged() {
        this.changeType = ChangeType.NO_CHANGE;
        return this;
    }

    public DiffBuilder setBeforeValue(Object before) {
        this.before = before;
        return this;
    }

    public DiffBuilder setAfterValue(Object after) {
        this.after = after;
        return this;
    }

    public DiffBuilder setFieldDescription(String fieldDescription) {
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

            }
        }

        return new Diff(changeType, fieldDescription, before, after, identifier);
    }

}
