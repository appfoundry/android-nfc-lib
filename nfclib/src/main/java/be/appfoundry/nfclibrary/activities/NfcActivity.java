/*
 * NfcActivity.java
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

package be.appfoundry.nfclibrary.activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import be.appfoundry.nfclibrary.utilities.interfaces.NfcMessageUtility;
import be.appfoundry.nfclibrary.utilities.interfaces.NfcReadUtility;
import be.appfoundry.nfclibrary.utilities.sync.NfcMessageUtilityImpl;
import be.appfoundry.nfclibrary.utilities.sync.NfcReadUtilityImpl;

/**
 * Activity automatically subscribing in Foreground Dispatch Method - state.
 * Contains several convenience methods, such as automatically parsing received intents, parsing the result
 * from a SpareArray.
 *
 * @author Daneo Van Overloop
 *         NfcLibrary
 */
abstract public class NfcActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback {

    private static final String TAG = NfcActivity.class.getName();

    private static final String beamEnabled = "androidBeamEnabled";

    NfcAdapter mNfcAdapter;
    private List<String> mNfcMessageStrings;

    private NfcMessageUtility mNfcMessageUtility = new NfcMessageUtilityImpl();
    private NfcReadUtility mNfcReadUtility = new NfcReadUtilityImpl();

    private PendingIntent pendingIntent;
    private IntentFilter[] mIntentFilters;
    private String[][] mTechLists;
    private boolean mBeamEnabled;

    public NfcMessageUtility getNfcMessageUtility() {
        return mNfcMessageUtility;
    }

    public NfcReadUtility getNfcReadUtility() {
        return mNfcReadUtility;
    }

    /**
     * Initializes all fields and the NFC Adapter if present
     *
     * @param savedInstanceState
     *         containing state, propagated to super
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAdapter();
        initFields();
    }

    /**
     * When in RestoreInstance beam is automatically enabled if the flag - beamEnabled - is set as such
     *
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(@NotNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.getBoolean(beamEnabled)) {
            enableBeam();
        } else {
            disableBeam();
        }
    }


    /**
     * Automatically subscribes the ForegroundDispatch
     */
    public void onResume() {
        super.onResume();
        initAdapter();

        if (getNfcAdapter() != null) {
            getNfcAdapter().enableForegroundDispatch(this, pendingIntent, mIntentFilters, mTechLists);
            Log.d(TAG, "FGD enabled");
        }
    }

    /**
     * Method always called upon delivery from an intent. Used when in Foreground Dispatch mode.
     * Every intent subscribed for is delivered here. Sets the intent of the activity to the just received intent
     * Data is extracted to the getNfcMessages.
     *
     * @param intent
     *         containing data
     *
     * @post getIntent() == intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "Received intent!");
        setIntent(intent);
        if (getIntent() != null) {
            if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction()) || NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
                mNfcMessageStrings = transformSparseArrayToArrayList(mNfcReadUtility.readFromTagWithSparseArray(intent));
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(beamEnabled, mBeamEnabled);
    }

    /**
     * Disables ForegroundDispatch if NFC present
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (getNfcAdapter() != null) {
            getNfcAdapter().disableForegroundDispatch(this);
            Log.d(TAG, "FGD disabled");
        }
    }

    /**
     * Method called when attempting to beam.
     *
     * @param event
     *         delivered by system
     *
     * @return an NFC Message to auto-pair bluetooth, or Test text when no bluetooth is present.
     */
    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        return new NfcMessageUtilityImpl().createText("You're seeing this message because you have not overridden the createNdefMessage(NfcEvent event) in your activity.");

    }

    /**
     * @return the String representation of the data last parsed
     */
    protected List<String> getNfcMessages() {
        return mNfcMessageStrings;
    }

    /**
     * Initializes which intents and NfcTechnologies to filter for
     */
    private void initFields() {
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        mIntentFilters = new IntentFilter[]{new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)};
        mTechLists = new String[][]{new String[]{Ndef.class.getName()},
                new String[]{NdefFormatable.class.getName()}};
    }

    /**
     * Initializes the NFC Adapter if not present
     */
    private void initAdapter() {
        if (getNfcAdapter() == null) {
            mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
            Log.d(TAG, "Adapter initialized");
        }
    }

    /**
     * Enables Android beam, sets this class as receiver
     */
    protected void enableBeam() {
        if (getNfcAdapter() != null) {
            getNfcAdapter().setNdefPushMessageCallback(this, this);
            mBeamEnabled = true;
            Log.d(TAG, "Beam enabled");
        }

    }

    protected boolean beamEnabled() {
        return mBeamEnabled;
    }

    /**
     * Removes this class as an Android Beam receiver
     */
    protected void disableBeam() {
        if (getNfcAdapter() != null) {
            getNfcAdapter().setNdefPushMessageCallback(null, this);
            mBeamEnabled = false;
            Log.d(TAG, "Beam disabled");
        }
    }

    /**
     * Retrieve the current NFC Adapter
     *
     * @return null if no adapter present, else {@link NfcAdapter}
     */
    protected NfcAdapter getNfcAdapter() {
        return this.mNfcAdapter;
    }

    protected void setNfcAdapter(NfcAdapter nfcAdapter) {
        this.mNfcAdapter = nfcAdapter;
    }


    /**
     * Transforms a {@link android.util.SparseArray}<String> into a {@link java.util.List}\<String>
     *
     * @param sparseArray
     *         to transform
     *
     * @return either an empty list or a list containing the values from the original array
     */
    protected List<String> transformSparseArrayToArrayList(SparseArray<String> sparseArray) {
        List<String> list = new ArrayList<String>(sparseArray.size());
        for (int i = 0; i < sparseArray.size(); i++) {
            list.add(sparseArray.valueAt(i));
        }
        return list;
    }
}
