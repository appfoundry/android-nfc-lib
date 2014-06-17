/*
 * AsyncNfcWriteOperation.java
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

/**
 * NfcLibrary by daneo
 * Created on 22/04/14.
 */
public interface AsyncNfcWriteOperation {
    /**
     * Method executed asynchronously, do **NOT** execute any UI logic in here! Funky stuff may occur
     */
    void executeWriteOperation();

    /**
     * Method executed asynchronously, do **NOT** execute any UI logic in here ! Funky stuff may occur
     * @param intent to be passed to the write utility
     * @param args to be passed to the method
     */
    void executeWriteOperation(Intent intent, Object... args);
}
