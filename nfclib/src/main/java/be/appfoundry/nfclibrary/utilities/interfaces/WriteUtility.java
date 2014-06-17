/*
 * WriteUtility.java
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

package be.appfoundry.nfclibrary.utilities.interfaces;

import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.Tag;

import be.appfoundry.nfclibrary.exceptions.InsufficientCapacityException;
import be.appfoundry.nfclibrary.exceptions.ReadOnlyTagException;

/**
 * Interface containing a few convenience methods for the user. Layer 2 in abstraction.
 */
public interface WriteUtility extends NdefWrite {


    /**
     * Writes towards a {@link android.nfc.Tag}
     *
     * @param message
     *         to write
     * @param tag
     *         to write to
     *
     * @return true if success
     *
     * @see #writeToNdef(android.nfc.NdefMessage, android.nfc.tech.Ndef)
     */
    boolean writeSafelyToTag(NdefMessage message, Tag tag);

    /**
     * Write the given message to the tag
     *
     * @param message
     *         to write
     * @param tag
     *         to write to
     *
     * @return true if success
     *
     * @throws android.nfc.FormatException if the message is in an incorrect format
     * @throws be.appfoundry.nfclibrary.exceptions.InsufficientCapacityException if there is not enough space available on the tag
     * @throws be.appfoundry.nfclibrary.exceptions.ReadOnlyTagException when attempting to write to a read-only tag
     */
    boolean writeToTag(NdefMessage message, Tag tag) throws FormatException, ReadOnlyTagException, InsufficientCapacityException;

    /**
     * Used to mark the following operation as readonly
     * @return an instance of WriteUtility in order to chain
     */
    WriteUtility makeOperationReadOnly();
}
