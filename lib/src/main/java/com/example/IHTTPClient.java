package com.example;

import org.json.JSONObject;

/**
 * Created by Daisy on 11/1/15.
 */
public interface IHTTPClient {
    int SUCCESS_CODE = 200;
    String USER_AGENT = "Mozilla/5.0";

    public JSONObject getJSONResponse() throws HTTPClientException;
}
