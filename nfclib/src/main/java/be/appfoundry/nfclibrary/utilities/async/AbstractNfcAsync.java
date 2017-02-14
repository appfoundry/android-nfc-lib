/*
 * AbstractNfcAsync.java
 * NfcLibrary project.
 *
 * Created by : Daneo van Overloop - 17/6/2014.
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 AppFoundry. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 */

package be.appfoundry.nfclibrary.utilities.async;

import android.content.Intent;
import android.os.AsyncTask;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import be.appfoundry.nfclibrary.tasks.GenericTask;
import be.appfoundry.nfclibrary.tasks.interfaces.AsyncOperationCallback;
import be.appfoundry.nfclibrary.tasks.interfaces.AsyncUiCallback;
import be.appfoundry.nfclibrary.utilities.interfaces.AsyncNfcWriteOperation;
import be.appfoundry.nfclibrary.utilities.interfaces.NfcWriteUtility;

/**
 * Class utilizing a {@link be.appfoundry.nfclibrary.tasks.GenericTask} to write async
 * @author Daneo Van Overloop
 * NfcLibrary
 */
public abstract class AbstractNfcAsync implements AsyncNfcWriteOperation {

    protected NfcWriteUtility mNfcWriteUtility;

    protected AsyncOperationCallback mAsyncOperationCallback;
    protected AsyncUiCallback mAsyncUiCallback;

    /**
     * @param asyncUiCallback
     *         callback executed on the UI thread
     */
    public AbstractNfcAsync(final AsyncUiCallback asyncUiCallback) {
        setAsyncUiCallback(asyncUiCallback);
    }

    /**
     * @param nfcWriteUtility
     *         implementation of {@link be.appfoundry.nfclibrary.utilities.interfaces.NfcWriteUtility} to use
     *
     * @see #AbstractNfcAsync(be.appfoundry.nfclibrary.tasks.interfaces.AsyncUiCallback)
     */
    public AbstractNfcAsync(final AsyncUiCallback asyncUiCallback, NfcWriteUtility nfcWriteUtility) {
        setAsyncUiCallback(asyncUiCallback);
        setNfcWriteUtility(nfcWriteUtility);
    }

    /**
     * @param asyncOperationCallback
     *         callback executed on a background thread
     *
     * @see #AbstractNfcAsync(be.appfoundry.nfclibrary.tasks.interfaces.AsyncUiCallback)
     */
    public AbstractNfcAsync(final @Nullable AsyncUiCallback asyncUiCallback, final @NotNull AsyncOperationCallback asyncOperationCallback) {
        this(asyncUiCallback);
        setAsyncOperationCallback(asyncOperationCallback);
    }

    /**
     * Constructor taking an AsyncUI-, AsyncOperationCallback and an {@link be.appfoundry.nfclibrary.utilities.interfaces.NfcWriteUtility}
     *
     * @param nfcWriteUtility
     *         implementation of {@link be.appfoundry.nfclibrary.utilities.interfaces.NfcWriteUtility} to use
     *
     * @see #AbstractNfcAsync(be.appfoundry.nfclibrary.tasks.interfaces.AsyncUiCallback, be.appfoundry.nfclibrary.tasks.interfaces.AsyncOperationCallback)
     */
    public AbstractNfcAsync(final @Nullable AsyncUiCallback asyncUiCallback, final @NotNull AsyncOperationCallback asyncOperationCallback, final @NotNull NfcWriteUtility nfcWriteUtility) {
        this(asyncUiCallback, nfcWriteUtility);
        setAsyncOperationCallback(asyncOperationCallback);
    }

    protected AsyncUiCallback getAsyncUiCallback() {
        return mAsyncUiCallback;
    }

    protected void setAsyncUiCallback(AsyncUiCallback asyncUiCallback) {
        mAsyncUiCallback = asyncUiCallback;
    }

    protected AsyncOperationCallback getAsyncOperationCallback() {
        return mAsyncOperationCallback;
    }

    protected void setAsyncOperationCallback(AsyncOperationCallback asyncOperationCallback) {
        mAsyncOperationCallback = asyncOperationCallback;
    }

    protected NfcWriteUtility getNfcWriteUtility() {
        return mNfcWriteUtility;
    }

    protected void setNfcWriteUtility(NfcWriteUtility nfcWriteUtility) {
        mNfcWriteUtility = nfcWriteUtility;
    }

    /**
     * Creates an async task with the current {@link #getAsyncOperationCallback()} as action
     *
     * @throws java.lang.NullPointerException
     *         if {@link #getAsyncOperationCallback()} is null
     * @see AsyncNfcWriteOperation#executeWriteOperation()
     */
    @Override
    public void executeWriteOperation() {
        if (getNfcWriteUtility() != null) {
            new GenericTask(mAsyncUiCallback, getAsyncOperationCallback(), getNfcWriteUtility()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            new GenericTask(mAsyncUiCallback, getAsyncOperationCallback()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    /**
     * Convenience method making it possible not to define an {@link be.appfoundry.nfclibrary.tasks.interfaces.AsyncOperationCallback} at construction time but rather pass arguments and let the implementation handle it
     * Should set the AsyncOperationCallback, and then call {@link #executeWriteOperation()}
     *
     * @param intent
     *         to be passed to the write utility
     * @param args
     *         to be passed to the method
     *
     * @see #setAsyncOperationCallback(be.appfoundry.nfclibrary.tasks.interfaces.AsyncOperationCallback)
     * @see AsyncNfcWriteOperation#executeWriteOperation()
     */
    @Override
    public abstract void executeWriteOperation(Intent intent, Object... args);

    /**
     * Used to check whether the passed type is equal to a String array
     *
     * @param type
     *         to compare with
     *
     * @return type.equals(String[].class)
     */
    protected boolean checkStringArguments(Class<?> type) {
        return (type.equals(String[].class));
    }

    /**
     * Used to check whether the passed type is equal to a Double array
     *
     * @param type
     *         to compare with
     *
     * @return type.equals(Double[].class)
     */
    protected boolean checkDoubleArguments(Class<?> type) {
        return type.equals(Double[].class);
    }
}
