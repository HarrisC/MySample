package com.example.inspiron.mysample.MultiThread;

/**
 * Created by Harris on 26/2/2018
 * Project Name MySample.
 */

public enum Priority {

    /*
      NOTE: DO NOT CHANGE ORDERING OF THOSE CONSTANTS UNDER ANY CIRCUMSTANCES.
      Doing so will make ordering incorrect.
     */

    /**
     * Lowest priority level. Used for prefetch of data.
     */
    LOW,

    /**
     * Medium priority level. Used for warming of data that might soon get visible.
     */
    MEDIUM,

    /**
     *  Default priority level.
     */
    DEFAULT,

    /**
     * Highest priority level. Used for data that are currently visible on screen.
     */
    HIGH,

    /**
     * Highest priority level. Used for data that are required instantly(mainly for emergency).
     */
    IMMEDIATE

}
