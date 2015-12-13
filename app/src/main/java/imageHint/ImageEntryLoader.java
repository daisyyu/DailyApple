package imageHint;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import com.example.daisy.dailyapple.Exceptions.NetworkExceptions;
import com.example.daisy.dailyapple.translation.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Daisy on 10/6/15.
 */
public class ImageEntryLoader extends AsyncTaskLoader<List<ImageEntry>> {
    //    private ProgressDialog dialog;
    private Context context;
    private String searchStr = "";

    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    static ImageEntry[] catEntries = new ImageEntry[]{
            new ImageEntry("http://lifewallpaperz.com/images/2015/cute-cat/cute-cat-01.jpg", "Cat1"),
            new ImageEntry("http://i.ytimg.com/vi/p2H5YVfZVFw/hqdefault.jpg", "Cat2"),
            new ImageEntry("http://www.mycatnames.com/wp-content/uploads/2015/09/Great-Ideas-for-cute-cat-names-2.jpg", "Cat3"),
    };

    @Override
    protected void onStartLoading() {
        Log.d("Daisy", "onStratLoading LearnItemEntryLoader");
//        dialog = ProgressDialog.show(context, "", "Please wait...");
        forceLoad();
    }

    @Override
    public List<ImageEntry> loadInBackground() {
        Log.d("Daisy", "loadInBackground LearnItemEntryLoader");
//        List<WordsEntry> result = new ArrayList<>();
//        result = Arrays.asList(imageEntries);
        try {
//            return getResultFromGoogleSearchAPI(searchStr);
            return getResultFromMicrosoftAzureAPI(searchStr);
        } catch (HTTPClientException e) {
            // TODO: wrap HTTPClientWITHGeneral exception and rethrow,
            // JSONPaser and HTTPClient is too fine grind.
            Log.d("Daisy", "HTTPClientException catched with error " +
                    "" + e);
            return null;
        } catch (JSONParserException e) {
            Log.d("Daisy", "JSONParserException catched with error " +
                    "" + e);
            return null;
        }
    }

    @Override
    public void deliverResult(List<ImageEntry> data) {
//        if (dialog.isShowing()) {
//            dialog.dismiss();
//        }
        super.deliverResult(data);
    }

    public ImageEntryLoader(Context context) {
        super(context);
        this.context = context;
    }

    private List<ImageEntry> getResultFromMicrosoftAzureAPI(final String
                                                                  strSearch)
            throws HTTPClientException, JSONParserException {
        ImageSearchHTTPClient.Builder imageSearchClientBuilder = new
                ImageSearchHTTPClient.Builder();
        IHTTPClient imageSearchClient = imageSearchClientBuilder.setWords
                (strSearch).build();
        JSONObject imageSearchClientResponseJSON = imageSearchClient
                .getJSONResponse();
        IJSONParser azureJSONParser = new AzureJSONParserImpl();
        AzureJSONParserImpl.Result result = (AzureJSONParserImpl
                .Result) azureJSONParser.extractResult
                (imageSearchClientResponseJSON);
        return result.getImageEntryList();
    }

    private List<ImageEntry> getResultFromGoogleSearchAPI(final String strSearch) throws NetworkExceptions {
        URL url;
        JSONObject json = null;
        BufferedReader reader = null;
        try {
            Log.v("Daisy", "LearnItemEntryLoader search string is " + searchStr);
            url = new URL("https://ajax.googleapis.com/ajax/services/search/images?" +
                    "v=1.0&q=" + strSearch + "&rsz=8");
            //&key=ABQIAAAADxhJjHRvoeM2WF3nxP5rCBRcGWwHZ9XQzXD3SWg04vbBlJ3EWxR0b0NVPhZ4xmhQVm3uUBvvRF-VAA&userip=192.168.0.172");

            URLConnection connection = url.openConnection();
            //connection.addRequestProperty("Referer", "http://technotalkative.com");
            String line;
            StringBuilder builder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

//            Log.v("Builder string => ", "" + builder.toString());

            json = new JSONObject(builder.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new NetworkExceptions("connecting to google image search " +
                    "API is failed", e);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {

                }
            }
        }
        try {
            // TODO: Check for response status;
            JSONObject responseObject = json.getJSONObject("responseData");
            JSONArray resultArray = responseObject.getJSONArray("results");
            Log.d("DYDEBUG", "Result array length => " + resultArray.length());
            Log.d("DYDEBUG", "Google image search API call result: " +
                    "" + resultArray.toString());
            List<ImageEntry> resultList = getImageList(resultArray);
            return resultList;
        } catch (JSONException e) {
            Log.e("DYDEBUG", "JSON passing exception");
            return Collections.emptyList();
        } catch (Exception e) {
            Log.e("DYDEBUG", "unexpected Exception during JSON parsing");
            return Collections.emptyList();
        }

    }

    private List<ImageEntry> getImageList(JSONArray resultArray) {
        List<ImageEntry> listImages = new ArrayList<>();
        ImageEntry bean;

        try {
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject obj;
                obj = resultArray.getJSONObject(i);
                bean = new ImageEntry();

                bean.setTitle(obj.getString("title"));
                bean.setIcon(obj.getString("tbUrl"));

                Log.v("Thumb URL => ", "" + obj.getString("tbUrl"));

                listImages.add(bean);

            }
            return listImages;
        } catch (JSONException e) {
            Log.d("DYDEBUG", "getImageList Exception");
            return Collections.emptyList();
        }
    }
}
