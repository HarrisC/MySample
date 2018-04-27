package com.example.inspiron.mysample.MultiThread;

import android.os.Process;

import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Harris on 26/2/2018
 * Project Name MySample.
 */

public class MultiThreadSupplier {

    // Number of cores available
    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    // thread pool executor for background tasks, 1 thread at a time;
    private final ThreadPoolExecutor mForSingleBackgroundTasks;

    // Thread pool executor for background tasks
    private final ThreadPoolExecutor mForBackgroundTasks;

    // Thread pool executor for bachground tasks, with priority
    private final PriorityThreadPoolExecutor mForPrioritizedBackgroundTasks;

    // Thread pool executor for light weight background tasks
//    private final ThreadPoolExecutor mForLightWeightBackgroundTasks;

    // Thread pool executor for main thread tasks
    private final Executor mMainThreadExecutor;

    // instance of MultiThreadSupplier
    private static MultiThreadSupplier sInstance;

    // static field to create single instance of MultiThreadSupplier;
    static {
        sInstance = new MultiThreadSupplier();
    }

    // Return the instance of MultiThreadSupplier
    public static MultiThreadSupplier getsInstance() {
        return sInstance;
    }

    // private constructor of MultiThreadSupplier
    private MultiThreadSupplier() {

//        Log.e("number of core", NUMBER_OF_CORES+"...");
        // Setting up the thread factory;
        ThreadFactory backgroundPriorityThreadFactory = new
                PriorityThreadFactory(Process.THREAD_PRIORITY_BACKGROUND);

        // Setting Up the thread pool executor for mForBackgroundTasks
        mForBackgroundTasks = new ThreadPoolExecutor(
                NUMBER_OF_CORES,
                NUMBER_OF_CORES,
                5,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                backgroundPriorityThreadFactory
        );

        mForPrioritizedBackgroundTasks = new PriorityThreadPoolExecutor(
                NUMBER_OF_CORES,
                NUMBER_OF_CORES,
                5,
                TimeUnit.SECONDS,
                backgroundPriorityThreadFactory
        );

        // Setting the thread pool executor for mForLightWeightBackgroundTasks
        /*mForLightWeightBackgroundTasks = new ThreadPoolExecutor(
                NUMBER_OF_CORES,
                NUMBER_OF_CORES,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                backgroundPriorityThreadFactory
        );*/

        mForSingleBackgroundTasks = new ThreadPoolExecutor(
                0,
                1,
                5,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                backgroundPriorityThreadFactory
        );

        // Setting up the thread pool executor for mMainThreadExecutor
        mMainThreadExecutor = new MainThreadExecutor();
    }

    // Return the thread pool executor for background task, such as http post request
    public ThreadPoolExecutor forBackgroundTasks() {
        return mForBackgroundTasks;
    }

    public Future submitBackgroundTask(Runnable runnable) {
        return mForBackgroundTasks.submit(runnable);
    }

    // Return the prioritized thread pool executor for background task
    public ThreadPoolExecutor forPrioritizedBackgroundTasks() {
        return mForPrioritizedBackgroundTasks;
    }

    // Return the thread pool executor for light weight background task, such as fetching data from local database
    /*public ThreadPoolExecutor getmForLightWeightBackgroundTasks() {
        return mForLightWeightBackgroundTasks;
    }*/

    // Return the thread pool executor for main thread task, simple UI task without communication in between such as updating progress bar
    public Executor forMainThreadTasks() {
        return mMainThreadExecutor;
    }

    // Return single threaded pool executor
    public ThreadPoolExecutor forSingleBackgroundThreadTasks() {
        return mForSingleBackgroundTasks;
    }
}
