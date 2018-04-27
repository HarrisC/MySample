package com.example.inspiron.mysample.MultiThread;

import android.os.Process;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.ThreadFactory;

/**
 * Created by Harris on 26/2/2018
 * Project Name MySample.
 */

public class PriorityThreadFactory implements ThreadFactory {
    private final int mThreadPriority;

    PriorityThreadFactory(int priority) {
        mThreadPriority = priority;
    }

    @Override
    public Thread newThread(@NonNull final Runnable runnable) {
        Runnable wrapperRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Process.setThreadPriority(mThreadPriority);
                } catch (Throwable t) {
                    Log.e(this.getClass().getSimpleName(), t.toString());
                }
                runnable.run();
            }
        };
        return new Thread(wrapperRunnable);
    }
}
