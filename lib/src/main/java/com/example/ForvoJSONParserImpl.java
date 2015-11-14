package com.example;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Daisy on 11/1/15.
 */
public class ForvoJSONParserImpl implements IJSONParser {


    @Override
    public IResult extractResult(JSONObject json) throws JSONParserException {
        Result result = new Result();
        try {
            JSONArray topLevelArray = json.getJSONArray("items");
            JSONObject topResult = topLevelArray.getJSONObject(0);
            String mp3Address = topResult.getString("pathmp3");
            result.setPhoneticMp3(mp3Address);
//            System.out.println(mp3Address);
        } catch (JSONException e) {
            throw new JSONParserException(e);
        }
        return result;
    }

    public static class Result implements IResult {
        private String phoneticMp3;

        public String getPhoneticMp3() {
            return phoneticMp3;
        }

        public void setPhoneticMp3(String phoneticMp3) {
            this.phoneticMp3 = phoneticMp3;
        }

    }
}
