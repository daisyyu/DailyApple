package com.example.daisy.dailyapple.Exceptions;

/**
 * Created by Daisy on 10/19/15.
 */
public class NetworkExceptions extends Exception{
    public NetworkExceptions(String detailMessage) {
        super(detailMessage);
    }

    public NetworkExceptions(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
