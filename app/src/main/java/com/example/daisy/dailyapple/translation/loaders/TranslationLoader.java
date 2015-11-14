package com.example.daisy.dailyapple.translation.loaders;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import com.example.daisy.dailyapple.translation.*;

import java.util.List;

/**
 * Created by Daisy on 11/3/15.
 */
public class TranslationLoader extends AsyncTaskLoader<IResult> {
    private String searchStr = "";

    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    public TranslationLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        Log.d("Daisy", "onStratLoading TranslationLoader");
        forceLoad();
    }

    @Override
    public IResult loadInBackground() {
        Log.d("Daisy", "loadInBackground TranslationLoader");

        return getTranslation(searchStr);

    }

    public IResult getTranslation(String searchStr) {
        TranslationHTTPClient.Builder translationClientBuilder = new
                TranslationHTTPClient
                        .Builder();
        IHTTPClient client = translationClientBuilder.setWords(searchStr).build();

        IJSONParser translationJSONParser = new YoudaoJSONParserImpl();
        try {
            IResult youdaoResult =
                    translationJSONParser
                            .extractResult(client.getJSONResponse());
            return youdaoResult;
        } catch (JSONParserException e) {
            Log.e("Daisy", "HTTPClientException in getTranslation return null " + e
                    .getMessage
                            ());
            IResult result = new YoudaoJSONParserImpl.Result();
            result.setResultStatus(false);
            return result;
        } catch (HTTPClientException e) {
            Log.e("Daisy", "HTTPClientException in getTranslation return null " + e
                    .getMessage
                            ());
            IResult result = new YoudaoJSONParserImpl.Result();
            result.setResultStatus(false);
            return result;
        }

    }

    @Override
    public void deliverResult(IResult data) {
        if (isReset()) {
            return;
        }
        super.deliverResult(data);
    }
}
