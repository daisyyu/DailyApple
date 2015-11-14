package com.example;

/**
 * Created by Daisy on 11/1/15.
 */
public class PhoneticHTTPClient extends AbstractHTTPClient {
    public enum PhoneticSex {
        FEMALE("f"), MALE("m");
        private String sex;
        public String getSex() {
            return sex;
        }

        PhoneticSex(String sex) {
            this.sex = sex;
        }

    }
    private PhoneticHTTPClient() {

    }

    private static String SERVER_NAME = "http://apifree.forvo.com";
    private static String KEY = "9a1e71d6b419a81f115bd1dbea95b466";
    private static String QUERY_PREFIX =
            "/format/json/action/word-pronunciations/language/en/";
    private static final String USER_AGENT = "Mozilla/5.0";
    private String word = "";
    private PhoneticSex phoneticSex = PhoneticSex.FEMALE;

    @Override
    protected String getQueryString() {
        return SERVER_NAME + QUERY_PREFIX + String.format
                ("key/%s/word/%s/sex/%s", KEY, word, phoneticSex.getSex());
    }

    public static class Builder {
        PhoneticHTTPClient client;

        public Builder() {
            client = new PhoneticHTTPClient();
        }

        public Builder setWords(String word) {
            client.word = word;
            return this;
        }

        public Builder setPhoneticSex(PhoneticSex phoneticSex) {
            client.phoneticSex = phoneticSex;
            return this;
        }

        public IHTTPClient build() {
            return client;
        }
    }
}
