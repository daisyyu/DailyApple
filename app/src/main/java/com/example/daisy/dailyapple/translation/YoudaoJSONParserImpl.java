package com.example.daisy.dailyapple.translation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by Daisy on 10/31/15.
 */
public class YoudaoJSONParserImpl implements IJSONParser {


    @Override
    public IResult extractResult(JSONObject json) throws JSONParserException {
        Result result = new Result();
        try {
            JSONArray translationJSONArray = json.getJSONArray
                    ("translation");

            result.setTranslations((String) translationJSONArray.get(0));

            JSONObject basicsJSONObject = json.getJSONObject("basic");
            result.setPhonetic(basicsJSONObject.getString("phonetic"));
            result.setExplains((basicsJSONObject.getJSONArray
                    ("explains"))
                    .toString());
            result.setResultStatus(true);
        } catch (JSONException e) {
            throw new JSONParserException(e);
        }
        return result;

    }

    public static class Result implements IResult {
        private String translations;
        private String phonetic;
        private String explains;
        private boolean isResultOK;

        public String getTranslations() {
            return translations;
        }

        public void setTranslations(String translations) {
            this.translations = translations;
        }

        public String getPhonetic() {
            return phonetic;
        }

        public void setPhonetic(String phonetic) {
            this.phonetic = phonetic;
        }

        public String getExplains() {
            return explains;
        }

        public void setExplains(String explains) {
            this.explains = explains;
        }

        @Override
        public boolean isOK() {
            return isResultOK;
        }

        @Override
        public void setResultStatus(boolean resultStatus) {
            isResultOK = resultStatus;
        }

    }
}
