package com.example.daisy.dailyapple.translation;

/**
 * Created by Daisy on 11/1/15.
 */
public class JSONParserException extends Exception{
    public JSONParserException() {
        super();
    }

    public JSONParserException(String message) {
        super(message);
    }

    public JSONParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public JSONParserException(Throwable cause) {
        super(cause);
    }
}
