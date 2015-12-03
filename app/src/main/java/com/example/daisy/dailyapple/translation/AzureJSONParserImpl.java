package com.example.daisy.dailyapple.translation;

import imageHint.CatEntry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daisy on 11/1/15.
 */
public class AzureJSONParserImpl implements IJSONParser {


    @Override
    public IResult extractResult(JSONObject json) throws JSONParserException {
        Result result = new Result();
        try {
            JSONObject topJsonObject = json.getJSONObject("d");
            JSONArray resultJsonArray = topJsonObject.getJSONArray
                    ("results");
            List<CatEntry> catEntryList = new ArrayList<>();
            for (int i = 0; i < resultJsonArray.length(); i++) {
                JSONObject jsonObject = resultJsonArray.getJSONObject(i);
                CatEntry catEntry = new CatEntry(jsonObject.getString
                        ("MediaUrl"), "");
                catEntryList.add(catEntry);
            }
            result.setCatEntryList(catEntryList);
            result.setResultStatus(true);
        } catch (JSONException e) {
            throw new JSONParserException(e);
        }
        return result;
    }

    public static class Result implements IResult {
        private List<CatEntry> catEntryList;
        private boolean isResultOK;

        @Override
        public boolean isOK() {
            return isResultOK;
        }

        @Override
        public void setResultStatus(boolean resultStatus) {
            isResultOK = resultStatus;
        }

        public List<CatEntry> getCatEntryList() {
            return catEntryList;
        }

        public void setCatEntryList(List<CatEntry> catEntryList) {
            this.catEntryList = catEntryList;
        }

    }
}
