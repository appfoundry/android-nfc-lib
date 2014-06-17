/*
 * WritePhoneNfcAsync.java
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
import android.nfc.FormatException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import be.appfoundry.nfclibrary.exceptions.InsufficientCapacityException;
import be.appfoundry.nfclibrary.exceptions.ReadOnlyTagException;
import be.appfoundry.nfclibrary.exceptions.TagNotPresentException;
import be.appfoundry.nfclibrary.tasks.interfaces.AsyncOperationCallback;
import be.appfoundry.nfclibrary.tasks.interfaces.AsyncUiCallback;
import be.appfoundry.nfclibrary.utilities.interfaces.NfcWriteUtility;

/**
 * @author Daneo Van Overloop
 * NfcLibrary
 * Created on 22/04/14.
 */
public class WritePhoneNfcAsync extends AbstractNfcAsync {
    /**
     * Instantiates a new WritePhoneNfcAsync.
     *
     * @param asyncUiCallback the async ui callback
     */
    public WritePhoneNfcAsync(AsyncUiCallback asyncUiCallback) {
        super(asyncUiCallback);
    }

    /**
     * Instantiates a new WritePhoneNfcAsync.
     *
     * @param asyncUiCallback the async ui callback
     * @param asyncOperationCallback the async operation callback
     */
    public WritePhoneNfcAsync(@Nullable AsyncUiCallback asyncUiCallback, @NotNull AsyncOperationCallback asyncOperationCallback) {
        super(asyncUiCallback, asyncOperationCallback);
    }

    /**
     * Instantiates a new WritePhoneNfcAsync.
     *
     * @param asyncUiCallback the async ui callback
     * @param asyncOperationCallback the async operation callback
     * @param nfcWriteUtility the nfc write utility
     */
    public WritePhoneNfcAsync(@Nullable AsyncUiCallback asyncUiCallback, @NotNull AsyncOperationCallback asyncOperationCallback, @NotNull NfcWriteUtility nfcWriteUtility) {
        super(asyncUiCallback, asyncOperationCallback, nfcWriteUtility);
    }

    @Override
    public void executeWriteOperation(final Intent intent, final Object... args) {
        if (checkStringArguments(args.getClass()) || args.length != 1 || intent == null) {
            throw new UnsupportedOperationException("Invalid arguments");
        }

        setAsyncOperationCallback(new AsyncOperationCallback() {
            @Override
            public boolean performWrite(NfcWriteUtility writeUtility) throws ReadOnlyTagException, InsufficientCapacityException, TagNotPresentException, FormatException {
                return writeUtility.writeTelToTagFromIntent((String) args[0], intent);
            }
        });
        super.executeWriteOperation();
    }
}
