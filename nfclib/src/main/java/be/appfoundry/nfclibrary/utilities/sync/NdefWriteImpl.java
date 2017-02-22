/*
 * NdefWriteImpl.java
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
import android.nfc.TagLostException;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.util.Log;

import java.io.IOException;

import be.appfoundry.nfclibrary.exceptions.InsufficientCapacityException;
import be.appfoundry.nfclibrary.exceptions.ReadOnlyTagException;
import be.appfoundry.nfclibrary.utilities.interfaces.NdefWrite;

/**
 * Class used for writing the message towards an NFC tag
 * @author Daneo Van Overloop
 * NfcLibrary
 */
public class NdefWriteImpl implements NdefWrite {

    private static final String TAG = NdefWriteImpl.class.getName();

    private boolean mReadOnly = false;

    /**
     * Instantiates a new NdefWriteImpl.
     */
    public NdefWriteImpl() {

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeToNdef(NdefMessage message, Ndef ndef) throws ReadOnlyTagException, InsufficientCapacityException, FormatException {

        if (message == null || ndef == null) {
            return false;
        }

        int size = message.getByteArrayLength();

        try {

            ndef.connect();
            if (!ndef.isWritable()) {
                throw new ReadOnlyTagException();
            }
            if (ndef.getMaxSize() < size) {
                throw new InsufficientCapacityException();
            }
            ndef.writeNdefMessage(message);
            if (ndef.canMakeReadOnly() && mReadOnly) {
                ndef.makeReadOnly();
            } else if (mReadOnly) {
                throw new UnsupportedOperationException();
            }
            return true;
        } catch (IOException e) {
            Log.w(TAG, "IOException occurred", e);
        } finally {
            if (ndef.isConnected()) {
                try {
                    ndef.close();
                } catch (IOException e) {
                    Log.v(TAG, "IOException occurred at closing.", e);
                }
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeToNdefAndMakeReadonly(NdefMessage message, Ndef ndef) throws ReadOnlyTagException, InsufficientCapacityException, FormatException {
        setReadOnly(true);
        boolean result = writeToNdef(message, ndef);
        setReadOnly(false);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeToNdefFormatableAndMakeReadonly(NdefMessage message, NdefFormatable ndefFormat) throws FormatException {
        setReadOnly(true);
        boolean result = writeToNdefFormatable(message, ndefFormat);
        setReadOnly(false);

        return result;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeToNdefFormatable(NdefMessage message, NdefFormatable ndefFormatable) throws FormatException {
        if (ndefFormatable == null || message == null) {
            return false;
        }

        try {
            ndefFormatable.connect();
            if (mReadOnly) {
                ndefFormatable.formatReadOnly(message);
            } else {
                ndefFormatable.format(message);
            }

            return true;
        } catch (TagLostException e) {
            Log.d(TAG, "We lost our tag !", e);
        } catch (IOException e) {
            Log.w(TAG, "IOException occured", e);
        } catch (FormatException e) {
            Log.w(TAG, "Message is malformed occurred", e);
            throw e;
        } finally {
            if (ndefFormatable.isConnected()) {
                try {
                    ndefFormatable.close();
                } catch (IOException e) {
                    Log.w(TAG, "IOException occurred at closing.", e);
                }
            }
        }

        return false;
    }

    /**
     * Sets read only.
     *
     * @param readOnly the read only
     */
    void setReadOnly(boolean readOnly) {
        mReadOnly = readOnly;
    }
}