/*
 * WriteUtilityImpl.java
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

package be.appfoundry.nfclibrary.utilities.sync;

import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.util.Log;

import be.appfoundry.nfclibrary.exceptions.InsufficientCapacityException;
import be.appfoundry.nfclibrary.exceptions.ReadOnlyTagException;
import be.appfoundry.nfclibrary.utilities.interfaces.NdefWrite;
import be.appfoundry.nfclibrary.utilities.interfaces.WriteUtility;

/**
 * @author Daneo Van Overloop
 * NfcLibrary
 * Created on 11/04/14.
 */
public class WriteUtilityImpl implements WriteUtility {

    private static final String TAG = WriteUtilityImpl.class.getName();

    private boolean readOnly = false;

    private NdefWrite mNdefWrite;

    public WriteUtilityImpl() {
        setNdefWrite(new NdefWriteImpl());
    }

    /**
     * @param ndefWrite
     *         used to delegate writing to Tag
     *
     * @throws java.lang.NullPointerException
     *         when null
     */
    public WriteUtilityImpl(NdefWrite ndefWrite) {
        if (ndefWrite == null) {
            throw new NullPointerException("WriteUtility cannot be null");
        }
        setNdefWrite(ndefWrite);
    }

    private void setNdefWrite(NdefWrite ndefWrite) {
        this.mNdefWrite = ndefWrite;
    }

    @Override
    public boolean writeToNdef(NdefMessage message, Ndef ndef) throws ReadOnlyTagException, InsufficientCapacityException, FormatException {
        return mNdefWrite.writeToNdef(message, ndef);
    }

    @Override
    public boolean writeToNdefAndMakeReadonly(NdefMessage message, Ndef ndef) throws ReadOnlyTagException, InsufficientCapacityException, FormatException {
        return mNdefWrite.writeToNdefAndMakeReadonly(message, ndef);
    }

    @Override
    public boolean writeToNdefFormatable(NdefMessage message, NdefFormatable ndefFormatable) throws FormatException {
        return mNdefWrite.writeToNdefFormatable(message, ndefFormatable);
    }

    @Override
    public boolean writeToNdefFormatableAndMakeReadonly(NdefMessage message, NdefFormatable ndefFormat) throws FormatException {
        return mNdefWrite.writeToNdefFormatableAndMakeReadonly(message, ndefFormat);
    }

    @Override
    public boolean writeSafelyToTag(NdefMessage message, Tag tag) {
        try {
            writeToTag(message, tag);
        } catch (ReadOnlyTagException e) {
            Log.d(TAG, "Tag is Read only !", e);
        } catch (InsufficientCapacityException e) {
            Log.d(TAG, "The tag's capacity is insufficient!", e);
        } catch (FormatException e) {
            Log.d(TAG, "The message is malformed!", e);
        }

        return false;
    }

    @Override
    public boolean writeToTag(NdefMessage message, Tag tag) throws FormatException, ReadOnlyTagException, InsufficientCapacityException {
        Ndef ndef = Ndef.get(tag);
        NdefFormatable formatable = NdefFormatable.get(tag);

        boolean result;
        if (readOnly) {
            result = writeToNdefAndMakeReadonly(message, ndef) || writeToNdefFormatableAndMakeReadonly(message, formatable);
        } else {
            result = writeToNdef(message, ndef) || writeToNdefFormatable(message, formatable);
        }

        readOnly = false;
        return result;
    }

    public WriteUtility makeOperationReadOnly() {
        readOnly = true;
        return this;
    }
}
