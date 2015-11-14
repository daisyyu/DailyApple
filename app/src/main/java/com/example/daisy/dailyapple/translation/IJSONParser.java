package com.example.daisy.dailyapple.translation;

import org.json.JSONObject;

/**
 * Created by Daisy on 10/31/15.
 */
public interface IJSONParser {
    public IResult extractResult(JSONObject json) throws JSONParserException;
}
