/*
 * NfcWriteUtility.java
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
import android.nfc.FormatException;
import android.nfc.NdefMessage;

import org.jetbrains.annotations.NotNull;

import be.appfoundry.nfclibrary.exceptions.InsufficientCapacityException;
import be.appfoundry.nfclibrary.exceptions.ReadOnlyTagException;
import be.appfoundry.nfclibrary.exceptions.TagNotPresentException;

/**
 * NfcLibrary by daneo
 * Created on 17/04/14.
 */
public interface NfcWriteUtility {
    /**
     * @param urlAddress
     *         The url, do not put in any prefix, {@link be.appfoundry.nfclibrary.constants.NfcPayloadHeader#HTTP_WWW} is auto added.
     * @param intent
     *         to write to
     *
     * @return true if successful
     */
    boolean writeUriToTagFromIntent(@NotNull String urlAddress, @NotNull Intent intent) throws FormatException, ReadOnlyTagException, InsufficientCapacityException, TagNotPresentException;

    /**
     * @param urlAddress
     *         The url, do not put in any prefix, {@link be.appfoundry.nfclibrary.constants.NfcPayloadHeader#HTTP_WWW} is auto added.
     * @param intent
     *         to write to
     *
     * @return true if successful
     */
    boolean writeUriWithPayloadToTagFromIntent(@NotNull String urlAddress, byte payloadHeader, @NotNull Intent intent) throws FormatException, ReadOnlyTagException, InsufficientCapacityException, TagNotPresentException;


    /**
     * Writes a telephone number to the tag
     *
     * @param telephone
     *         number to write
     * @param intent
     *         to write to
     *         e to
     *
     * @return true if success
     */
    boolean writeTelToTagFromIntent(@NotNull String telephone, @NotNull Intent intent) throws FormatException, ReadOnlyTagException, InsufficientCapacityException, TagNotPresentException;

    /**
     * Write SMS to tag. Due to a bug in Android this is not correctly implemented by the OS.
     *
     * @param number
     *         of the recipient
     * @param message
     *         to send to the person
     * @param intent
     *         to write to
     *
     * @return true if success
     */
    boolean writeSmsToTagFromIntent(@NotNull String number, String message, @NotNull Intent intent) throws FormatException, ReadOnlyTagException, InsufficientCapacityException, TagNotPresentException;

    /**
     * @param latitude
     *         maximum 6 decimals
     * @param longitude
     *         maximum 6 DECIMALS
     * @param intent
     *         to to write to
     *
     * @return true if success
     */
    boolean writeGeolocationToTagFromIntent(Double latitude, Double longitude, @NotNull Intent intent) throws FormatException, ReadOnlyTagException, InsufficientCapacityException, TagNotPresentException;

    /**
     * Write recipient, subject and message to tag
     *
     * @param recipient
     *         to whom the mail should be sent
     * @param subject
     *         of the email
     * @param message
     *         body of the email
     * @param intent
     *         to write to
     *
     * @return true if success
     */
    boolean writeEmailToTagFromIntent(@NotNull String recipient, String subject, String message, @NotNull Intent intent) throws FormatException, ReadOnlyTagException, InsufficientCapacityException, TagNotPresentException;

    /**
     * Write the bluetooth address to the tag
     *
     * @param macAddress
     *         to write to the tag. Must be in format XX:XX:XX:XX:XX:XX, separator may differ
     * @param intent
     *         to write to
     *
     * @return true if success
     */
    boolean writeBluetoothAddressToTagFromIntent(@NotNull String macAddress, Intent intent) throws InsufficientCapacityException, FormatException, ReadOnlyTagException, TagNotPresentException;


    /**
     * Pass a raw NdefMessage along to write
     * @param message to write to the tag
     * @param intent to write to
     * @return true if success
     * @throws FormatException
     */
    boolean writeNdefMessageToTagFromIntent(@NotNull NdefMessage message, Intent intent) throws FormatException, TagNotPresentException, ReadOnlyTagException, InsufficientCapacityException;

    /**
     * Pass a raw NdefMessage along to write
     * @param message to write to the tag
     * @param intent to write to
     * @return true if success
     * @throws FormatException
     */
    boolean writeTextToTagFromIntent(@NotNull String message, Intent intent) throws FormatException, TagNotPresentException, ReadOnlyTagException, InsufficientCapacityException;

    /**
     * Used to mark the following operation as readonly
     *
     * @return an instance of WriteUtility in order to chain
     */
    NfcWriteUtility makeOperationReadOnly();

}
