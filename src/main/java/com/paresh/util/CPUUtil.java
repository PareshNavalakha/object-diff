package com.paresh.util;

public class CPUUtil {
    static int numberOfAvailableProcessors;
    static {
        numberOfAvailableProcessors=Runtime.getRuntime().availableProcessors();
        if(numberOfAvailableProcessors<4)
        {
            numberOfAvailableProcessors=4;
        }
    }

    public static int getNumberOfAvailableProcessors()
    {
        return numberOfAvailableProcessors;
    }

}
