/*
 * WriteEmailNfcAsync.java
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
public class WriteEmailNfcAsync extends AbstractNfcAsync {

    /**
     * Instantiates a new WriteEmailNfcAsync.
     *
     * @param uiCallback the ui callback
     * @see AbstractNfcAsync#AbstractNfcAsync(be.appfoundry.nfclibrary.tasks.interfaces.AsyncUiCallback)
     */
    public WriteEmailNfcAsync(@Nullable AsyncUiCallback uiCallback) {
        super(uiCallback);
    }

    /**
     * Instantiates a new WriteEmailNfcAsync.
     *
     * @param asyncUiCallback the async ui callback
     * @param asyncOperationCallback the async operation callback
     * @see AbstractNfcAsync#AbstractNfcAsync(be.appfoundry.nfclibrary.tasks.interfaces.AsyncUiCallback, be.appfoundry.nfclibrary.tasks.interfaces.AsyncOperationCallback)
     */
    public WriteEmailNfcAsync(@Nullable AsyncUiCallback asyncUiCallback, @NotNull AsyncOperationCallback asyncOperationCallback) {
        super(asyncUiCallback, asyncOperationCallback);
    }

    /**
     * Instantiates a new WriteEmailNfcAsync.
     *
     * @param asyncUiCallback the async ui callback
     * @param asyncOperationCallback the async operation callback
     * @param nfcWriteUtility the nfc write utility
      */
    public WriteEmailNfcAsync(@Nullable AsyncUiCallback asyncUiCallback, @NotNull AsyncOperationCallback asyncOperationCallback, @NotNull NfcWriteUtility nfcWriteUtility) {
        super(asyncUiCallback, asyncOperationCallback, nfcWriteUtility);
    }

    @Override
    public void executeWriteOperation(final Intent intent, Object... args) {
        if (!checkStringArguments(args.getClass()) || args.length == 0) {
            throw new UnsupportedOperationException("Incorrect arguments");
        }

        final String recipient = (String) args[0], subject = args.length > 1 ? (String) args[1] : null, message = args.length == 3 ? (String) args[2] : null;

        setAsyncOperationCallback(new AsyncOperationCallback() {
            @Override
            public boolean performWrite(NfcWriteUtility writeUtility) throws ReadOnlyTagException, InsufficientCapacityException, TagNotPresentException, FormatException {

                return writeUtility.writeEmailToTagFromIntent(recipient, subject, message, intent);
            }
        });

        super.executeWriteOperation();
    }

}
