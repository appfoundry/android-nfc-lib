/*
 * AsyncUiCallback.java
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

package be.appfoundry.nfclibrary.tasks.interfaces;

/**
 * Interface providing callback methods which are ensured to be executed on the UI thread.
 * @author Daneo Van Overloop
 * NfcLibrary
 * Created on 18/04/14.
 */
public interface AsyncUiCallback {

    /**
     * Used to deliver the result of the operation.
     * @param result of the operation
     */
    void callbackWithReturnValue(Boolean result);

    /**
     * Used to signal current state of the task.
     * @param values containing the state in values[0]
     */
    void onProgressUpdate(Boolean... values);

    /**
     * When any error occurs, the error is passed here.
     * @param e the cause of the task to stopping or malfunctioning
     */
    void onError(Exception e);
}
