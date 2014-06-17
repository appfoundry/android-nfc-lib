/*
 * NfcMessageUtility.java
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

import org.jetbrains.annotations.NotNull;

import be.appfoundry.nfclibrary.exceptions.InsufficientCapacityException;
import be.appfoundry.nfclibrary.exceptions.ReadOnlyTagException;

/**
 * Interface containing the highest level methods, providing the user with a total abstraction of the way NFC works, layer 3 in abstraction.
 */
public interface NfcMessageUtility {
    /**
     * @param urlAddress
     *         The url, auto-prefixed with the http://www. header
     * @return true if successful
     */
    NdefMessage createUri(@NotNull String urlAddress) throws FormatException;

    /**
     * Creates a telephone number - NdefMessage
     *
     * @param telephone
     *         number to create
     * @return true if success
     */
    NdefMessage createTel(@NotNull String telephone) throws FormatException;

    /**
     * Create SMS - NdefMessage. Due to a bug in Android this is not correctly implemented by the OS.
     *
     * @param message
     *         to send to the person
     * @param number
     *         of the recipient
     * @return true if success
     */
    NdefMessage createSms(@NotNull String number, String message) throws FormatException;

    /**
     * Creates a Geolocation - NdefMessage.
     * @param latitude
     *         maximum 6 decimals
     * @param longitude
     *         maximum 6 DECIMALS
     * @return true if success
     */
    NdefMessage createGeolocation(Double latitude, Double longitude) throws FormatException;

    /**
     * Create recipient, subject and message email - NdefMessage
     *
     * @param recipient
     *         to whom the mail should be sent
     * @param subject
     *         of the email
     * @param message
     *         body of the email
     * @return true if success
     */
    NdefMessage createEmail(@NotNull String recipient, String subject, String message) throws FormatException;

    /**
     * Create the bluetooth address - NdefMessage
     *
     * @param macAddress
     *         to create NdefMessage. Must be in format XX:XX:XX:XX:XX:XX, separator may differ
     * @return true if success
     */
    NdefMessage createBluetoothAddress(@NotNull String macAddress) throws InsufficientCapacityException, FormatException, ReadOnlyTagException;


    /**
     * Create the message with plain text
     * @param text to transfer
     * @return prepared NdefMessage
     * @throws FormatException
     */
    NdefMessage createText(@NotNull String text) throws FormatException;

    /**
     * Create the address with the given header
     * @see #createUri(String)
     */
    NdefMessage createUri(String urlAddress, byte payloadHeader) throws FormatException;
}
