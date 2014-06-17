/*
 * WriteCallbackNfcAsync.java
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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import be.appfoundry.nfclibrary.tasks.interfaces.AsyncOperationCallback;
import be.appfoundry.nfclibrary.tasks.interfaces.AsyncUiCallback;
import be.appfoundry.nfclibrary.utilities.interfaces.NfcWriteUtility;

/**
 * @author Daneo Van Overloop
 * NfcLibrary
 * Created on 24/04/14.
 */
public class WriteCallbackNfcAsync extends AbstractNfcAsync {

    /**
     * Instantiates a new WriteCallbackNfcAsync.
     *
     * @param asyncUiCallback the async ui callback
     */
    public WriteCallbackNfcAsync(AsyncUiCallback asyncUiCallback) {
        super(asyncUiCallback);
    }

    /**
     * Instantiates a new WriteCallbackNfcAsync.
     *
     * @param asyncUiCallback the async ui callback
     * @param asyncOperationCallback the async operation callback
     */
    public WriteCallbackNfcAsync(@Nullable AsyncUiCallback asyncUiCallback, @NotNull AsyncOperationCallback asyncOperationCallback) {
        super(asyncUiCallback, asyncOperationCallback);
    }

    /**
     * Instantiates a new WriteCallbackNfcAsync.
     *
     * @param asyncUiCallback the async ui callback
     * @param asyncOperationCallback the async operation callback
     * @param nfcWriteUtility the nfc write utility
     */
    public WriteCallbackNfcAsync(@Nullable AsyncUiCallback asyncUiCallback, @NotNull AsyncOperationCallback asyncOperationCallback, @NotNull NfcWriteUtility nfcWriteUtility) {
        super(asyncUiCallback, asyncOperationCallback, nfcWriteUtility);
    }

    @Override
    public void executeWriteOperation(Intent intent, Object... args) {
        super.executeWriteOperation();
    }
}
