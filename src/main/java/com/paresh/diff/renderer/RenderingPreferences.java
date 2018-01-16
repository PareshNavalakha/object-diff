package com.paresh.diff.renderer;

public interface RenderingPreferences {

    void setPreference(String propertyName, Object propertyValue);

    Object getPreference(String propertyName);
}
