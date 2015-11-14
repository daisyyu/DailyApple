package com.example.daisy.dailyapple.translation;

/**
 * Created by Daisy on 11/1/15.
 */
public class HTTPClientException extends Exception{
    public HTTPClientException(Throwable cause) {
        super(cause);
    }

    public HTTPClientException(String message) {
        super(message);
    }

    public HTTPClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public HTTPClientException() {
        super();
    }
}
