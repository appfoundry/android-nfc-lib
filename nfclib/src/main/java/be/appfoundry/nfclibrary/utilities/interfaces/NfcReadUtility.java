/*
 * NfcReadUtility.java
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

import android.content.Intent;
import android.nfc.NdefMessage;

import java.util.Map;

/**
 * Interface for the Read utility, containing methods which could be convenient
 */
public interface NfcReadUtility {

    /**
     * Read data from received {@link android.content.Intent}
     *
     * @param nfcDataIntent
     *         the intent containing the data to read
     *
     * @return the data in an array of {@link android.content.Intent}'s or an empty {@link android.util.SparseArray} array
     */
    android.util.SparseArray<String> readFromTagWithSparseArray(Intent nfcDataIntent);

    /**
     * Read data from received {@link android.content.Intent}
     * @param nfcDataIntent the intent containing the data to read
     * @return the data is either a filled or empty {@link java.util.Map} depending on whether parsing was successful
     */
    Map<Byte,String> readFromTagWithMap(Intent nfcDataIntent);


    /**
     * Retrieve the content type from the message
     * @param message type {@link be.appfoundry.nfclibrary.constants.NfcPayloadHeader}
     * @return {@link be.appfoundry.nfclibrary.constants.NfcPayloadHeader}
     */
    java.util.Iterator<Byte> retrieveMessageTypes(NdefMessage message);

    /**
     * Retrieve the actual message content
     * @param message to parse
     * @return the formatted message
     */
    String retrieveMessage(NdefMessage message);
}
