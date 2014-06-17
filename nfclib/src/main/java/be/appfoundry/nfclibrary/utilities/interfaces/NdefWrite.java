/*
 * NdefWrite.java
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
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;

import be.appfoundry.nfclibrary.exceptions.InsufficientCapacityException;
import be.appfoundry.nfclibrary.exceptions.ReadOnlyTagException;

/**
 * Interface for the lowest level write methods, layer 1 in abstraction.
 */
public interface NdefWrite {
    /**
     * Write message to ndef
     *
     * @param message
     *         to write
     * @param ndef
     *         from tag to write to
     *
     * @return true if success, false if ndef == null || message == null
     *
     * @throws ReadOnlyTagException
     *         if tag is read-only
     * @throws InsufficientCapacityException
     *         if the tag's capacity is not sufficient
     * @throws FormatException
     *         if the message is malformed
     */
    boolean writeToNdef(NdefMessage message, Ndef ndef) throws ReadOnlyTagException, InsufficientCapacityException, FormatException;

    /**
     * Write message to ndef and make readonly
     *
     * @see NdefWrite#writeToNdef(android.nfc.NdefMessage, android.nfc.tech.Ndef)
     */
    boolean writeToNdefAndMakeReadonly(NdefMessage message, Ndef ndef) throws ReadOnlyTagException, InsufficientCapacityException, FormatException;

    /**
     * Write the message to an NdefFormatable
     * @param message to write
     * @param ndefFormatable to write to
     * @return true if success, false if ndefFormatable == null || message == null
     * @throws FormatException
     */
    boolean writeToNdefFormatable(NdefMessage message, NdefFormatable ndefFormatable) throws FormatException;

    /**
     * Write the message to an NdefFormatable and make readonly
     * @see NdefWrite#writeToNdefFormatable(android.nfc.NdefMessage, android.nfc.tech.NdefFormatable)
     */
    boolean writeToNdefFormatableAndMakeReadonly(NdefMessage message, NdefFormatable ndefFormat) throws FormatException;

}
