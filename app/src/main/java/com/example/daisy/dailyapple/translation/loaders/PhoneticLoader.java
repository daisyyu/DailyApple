package com.example.daisy.dailyapple.translation.loaders;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import com.example.daisy.dailyapple.translation.*;

/**
 * Created by Daisy on 11/3/15.
 */
public class PhoneticLoader extends AsyncTaskLoader<IResult> {
    private String searchStr = "";

    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    public PhoneticLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        Log.d("Daisy", "onStratLoading PhoneticLoader");
        forceLoad();
    }

    @Override
    public IResult loadInBackground() {
        Log.d("Daisy", "loadInBackground PhoneticLoader");

        return getPhonetic(searchStr);

    }


    public IResult getPhonetic(String searchStr) {
        //TODO: ingect builder and extract loader as  transaltion
        // fragment loader
        PhoneticHTTPClient.Builder translationClientBuilder = new
                PhoneticHTTPClient
                        .Builder();
        IHTTPClient client = translationClientBuilder.setWords(searchStr).build();

        IJSONParser phoneticJSONParser = new ForvoJSONParserImpl();
        try {
            IResult result =
                    phoneticJSONParser
                            .extractResult(client.getJSONResponse());
            return result;
        } catch (JSONParserException e) {
            Log.e("Daisy", "JSONParserException in getPhonetic return null " + e
                    .getMessage
                    ());
            IResult result = new ForvoJSONParserImpl.Result();
            result.setResultStatus(false);
            return result;
        } catch (HTTPClientException e) {
            Log.e("Daisy","HTTPClientException in getPhonetic return null "+ e
                    .getMessage
                    ());
            IResult result = new ForvoJSONParserImpl.Result();
            result.setResultStatus(false);
            return result;
        }

    }
    @Override
    public void deliverResult(IResult data) {
        if (isReset()){
            return;
        }
        super.deliverResult(data);
    }
}
