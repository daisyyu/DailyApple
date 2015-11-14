package com.example;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Daisy on 10/29/15.
 */
public class TranslationHTTPClient extends AbstractHTTPClient {
    private static String SERVER_NAME = "http://fanyi.youdao.com/openapi.do";
    private static String KEY = "673499549";
    private static String QUERY_PREFIX =
            "?keyfrom=daisyfirstapp&type=data&doctype=json&version=1.1&";
    private static final String USER_AGENT = "Mozilla/5.0";
    private String word ="";

    private TranslationHTTPClient() {

    }

    @Override
    protected String getQueryString() {
        return SERVER_NAME + QUERY_PREFIX + String.format
                ("key=%s&q=%s", KEY,
                        word);
    }

    public static class Builder {
        TranslationHTTPClient client;

        public Builder() {
            client = new TranslationHTTPClient();
        }

        public Builder setWords(String word) {
            client.word = word;
            return this;
        }

        public IHTTPClient build() {
            return client;
        }


    }
}
