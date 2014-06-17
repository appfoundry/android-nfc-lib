/*
 * GenericTaskTestsAsync.java
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
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.HandlerThread;
import android.util.Log;

import be.appfoundry.nfclibrary.exceptions.InsufficientCapacityException;
import be.appfoundry.nfclibrary.exceptions.ReadOnlyTagException;
import be.appfoundry.nfclibrary.exceptions.TagNotPresentException;
import be.appfoundry.nfclibrary.tasks.interfaces.AsyncOperationCallback;
import be.appfoundry.nfclibrary.utilities.TestUtilities;
import be.appfoundry.nfclibrary.utilities.interfaces.NfcWriteUtility;

/**
 * NfcLibrary by daneo
 * Created on 28/05/14.
 */
public class GenericTaskTestsAsync extends AbstractFailsTestsAsync {

    private static final String TAG = GenericTaskTestsAsync.class.getSimpleName();

    public GenericTaskTestsAsync() {
        mHandlerThread = new HandlerThread("Test") {
            @Override
            public void run() {
                if (mAsyncNfcWriteOperation != null) {
                    mAsyncNfcWriteOperation.executeWriteOperation();
                } else {
                    Log.d(TAG, "EmailAsync = null!");
                }
                release();
            }
        };
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testWriteFailedNdef() throws InterruptedException {
        performAsyncTaskWithReturnValue(getSucceedingAsyncUiCallback(), getFailingAsyncOperationCallback(), mTestUtilities.mockFailNdefWrite());
        assertFalse("Result was true!", checkResult("derp.com"));
    }

    public void testWriteFailedNdefFormatable() throws InterruptedException {
        performAsyncTaskWithReturnValue(getSucceedingAsyncUiCallback(), getFailingAsyncOperationCallback(), mTestUtilities.mockFailNdefWrite());
        assertFalse("Result was true!", checkResult("derp.com"));
    }


    public void testWriteSucceededNdef() throws InterruptedException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        performAsyncTaskWithReturnValue(getSucceedingAsyncUiCallback(), getSucceedingAsyncOperationCallback(), mTestUtilities.mockNdefWriteUtility());
        assertTrue("Result was false!", checkResult("derp.com"));
    }

    public void testWriteSucceededNdefFormatable() throws InterruptedException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        performAsyncTaskWithReturnValue(getSucceedingAsyncUiCallback(), getSucceedingAsyncOperationCallback(), mTestUtilities.mockNdefFormatableWrite());
        assertTrue("Result was false!", checkResult("derp.com"));
    }


    private AsyncOperationCallback getSucceedingAsyncOperationCallback() throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        final Tag mockTag = mTestUtilities.mockTag(TestUtilities.NDEF);
        final Intent intent = new Intent().putExtra(NfcAdapter.EXTRA_TAG, mockTag);
        return new AsyncOperationCallback() {
            @Override
            public boolean performWrite(NfcWriteUtility writeUtility) throws ReadOnlyTagException, InsufficientCapacityException, TagNotPresentException, FormatException {
                return true;
            }
        };
    }

    private AsyncOperationCallback getFailingAsyncOperationCallback() {
        return new AsyncOperationCallback() {
            @Override
            public boolean performWrite(NfcWriteUtility writeUtility) throws ReadOnlyTagException, InsufficientCapacityException, TagNotPresentException, FormatException {
                return false;
            }
        };
    }


}
