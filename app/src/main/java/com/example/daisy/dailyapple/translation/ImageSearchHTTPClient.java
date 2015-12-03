package com.example.daisy.dailyapple.translation;

import android.util.Base64;

/**
 * Created by Daisy on 10/29/15.
 */
public class ImageSearchHTTPClient extends AbstractHTTPClient {
    private static String SERVER_NAME = "https://api.datamarket.azure" +
            ".com/Bing/Search/Image";
    private static String QUERY_PREFIX =
            "?$format=json&ImageFilters=%27Size%3aMedium%27&$top=8&";
    //    "";
    private static final String USER_AGENT = "Mozilla/5.0";
    private String word = "";
    private static final String ACCOUNT_KEY =
            "Ezq/A6dVAvwdhgVen+uaUd8p7CH2qbHecbG/+enOVN0";

    private ImageSearchHTTPClient() {

    }

    @Override
    protected String getQueryString() {
        return SERVER_NAME + QUERY_PREFIX +
                "Query=%27" + word + "%27";
    }

    protected String getAccountKey() {
        byte[] accountKeyBytes = Base64.encode
                ((ACCOUNT_KEY + ":" + ACCOUNT_KEY).getBytes(), Base64.NO_WRAP);
        return new String(accountKeyBytes);
    }

    public static class Builder {
        ImageSearchHTTPClient client;

        public Builder() {
            client = new ImageSearchHTTPClient();
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
