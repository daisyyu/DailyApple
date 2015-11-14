package com.example;

import org.json.JSONException;
import org.json.JSONObject;

public class MyClass {
    public static void main(String args[]) throws HTTPClientException, JSONParserException {
        System.out.println("prog starts");
        TranslationHTTPClient.Builder translationClientBuilder = new
                TranslationHTTPClient
                .Builder();
        IHTTPClient client = translationClientBuilder.setWords("good").build();

        IJSONParser translationJSONParser = new YoudaoJSONParserImpl();
        YoudaoJSONParserImpl.Result youdaoResult = (YoudaoJSONParserImpl.Result) translationJSONParser
        .extractResult(client
                .getJSONResponse());
        System.out.println(youdaoResult.getTranslations());
        System.out.println(youdaoResult.getPhonetic());
        System.out.println(youdaoResult.getExplains());
        PhoneticHTTPClient.Builder phoneticClientBuilder = new PhoneticHTTPClient
                .Builder();

        IHTTPClient phoneticlient = phoneticClientBuilder.setWords("good")
                .setPhoneticSex(PhoneticHTTPClient.PhoneticSex.FEMALE)
                .build();
        JSONObject phoneticClientResponseJSON = phoneticlient.getJSONResponse();
        IJSONParser phoneticJSONParser = new ForvoJSONParserImpl();
        ForvoJSONParserImpl.Result forvoResult= (ForvoJSONParserImpl.Result) phoneticJSONParser.extractResult
                (phoneticClientResponseJSON);
        System.out.println(forvoResult.getPhoneticMp3());
    }
}
