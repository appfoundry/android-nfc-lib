/*
 * NfcReadUtilityImpl.java
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

import android.content.Intent;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.util.SparseArray;

import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import be.appfoundry.nfclibrary.constants.NfcType;
import be.appfoundry.nfclibrary.utilities.interfaces.NfcReadUtility;

/**
 * @author Daneo Van Overloop
 * NfcLibrary
 * Created on 28/03/14.
 */

public class NfcReadUtilityImpl implements NfcReadUtility {

    private static final String TAG = NfcReadUtilityImpl.class.getCanonicalName();


    /**
     * {@inheritDoc}
     */
    @Override
    public SparseArray<String> readFromTagWithSparseArray(Intent nfcDataIntent) {
        Parcelable[] messages = nfcDataIntent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        SparseArray<String> resultMap = messages != null ? new SparseArray<String>(messages.length) : new SparseArray<String>();

        if (messages == null) {
            return resultMap;
        }

        for (Parcelable message : messages) {
            for (NdefRecord record : ((NdefMessage) message).getRecords()) {
                byte type = retrieveTypeByte(record.getPayload());

                String i = resultMap.get(type);
                if (i == null) {
                    resultMap.put(type, parseAccordingToType(record));
                }
            }
        }

        return resultMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Byte, String> readFromTagWithMap(Intent nfcDataIntent) {
        Map<Byte, String> resultMap = new HashMap<Byte, String>();
        SparseArray<String> sparseArray = readFromTagWithSparseArray(nfcDataIntent);

        for (int i = 0; i < sparseArray.size(); i++) {
            resultMap.put((byte) sparseArray.keyAt(i), sparseArray.valueAt(i));
        }
        return resultMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Byte> retrieveMessageTypes(NdefMessage record) {
        Collection<Byte> list = new ArrayList<Byte>();
        for (NdefRecord ndefRecord : record.getRecords()) {
            list.add(retrieveTypeByte(ndefRecord.getPayload()));
        }
        return list.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String retrieveMessage(NdefMessage message) {
        return message.getRecords()[0] != null ? parseAccordingToHeader(message.getRecords()[0].getPayload()) : null;
    }

    private byte retrieveTypeByte(byte[] payload) {
        if (payload.length > 0) {
            return payload[0];
        }

        return -1;
    }

    private String parseAccordingToHeader(@NotNull byte[] payload) {
        return (payload.length > 0) ? new String(payload, 1, payload.length - 1, Charset.forName("US-ASCII")).trim() : "";
    }

    private String parseAccordingToType(NdefRecord obj) {
        if (Arrays.equals(obj.getType(), NfcType.BLUETOOTH_AAR)) {

            byte[] toConvert = obj.getPayload();
            StringBuilder result = new StringBuilder();
            for (int i = toConvert.length - 1; i >= 2; i--) {
                byte temp = toConvert[i];
                String tempString = ((temp < 0) ? Integer.toHexString(temp + Byte.MAX_VALUE) : Integer.toHexString(temp));
                result.append((tempString.length() < 2) ? "0" + tempString : tempString);
                result.append(":");
            }
            return !(result.length() == 0) ? result.substring(0, result.length() - 1) : result.toString();
        }

        return Uri.parse(parseAccordingToHeader(obj.getPayload())).toString();
    }

}



