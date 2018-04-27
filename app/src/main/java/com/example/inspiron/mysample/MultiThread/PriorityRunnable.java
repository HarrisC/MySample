package com.example.inspiron.mysample.MultiThread;

/**
 * Created by Harris on 26/2/2018
 * Project Name MySample.
 */

public class PriorityRunnable implements Runnable {
    private final Priority priority;

    // default priority medium
    public PriorityRunnable() {
        priority = Priority.DEFAULT;
    }

    public PriorityRunnable(Priority priority) {
        this.priority = priority;
    }

    @Override
    public void run() {
        // nothing to do here.
    }

    Priority getPriority() {
        return priority;
    }
}
