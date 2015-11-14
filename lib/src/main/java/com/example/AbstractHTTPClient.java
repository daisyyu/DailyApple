package com.example;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Daisy on 11/1/15.
 */
public abstract class AbstractHTTPClient implements IHTTPClient {
    @Override
    public JSONObject getJSONResponse() throws HTTPClientException {
        String queryString = getQueryString();
        return getJSONResponseFromService(queryString);
    }

    protected JSONObject getJSONResponseFromService(String query) throws HTTPClientException {
        JSONObject result = null;
        try {
            URL urlRequest = new URL(query);
//            System.out.println("query is: " +query);
            HttpURLConnection con = (HttpURLConnection) urlRequest.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            if (responseCode != SUCCESS_CODE) {
                throw new HTTPClientException("HTTP call failed with response " +
                        "code of " + responseCode);
            }
//            System.out.println("responseCode is: " + responseCode);


            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
//            System.out.println(response.toString());

            // result already obtained
            result = new JSONObject(response.toString());
        } catch (MalformedURLException e) {
            throw new HTTPClientException("MalformedURLException", e);
        } catch (IOException e) {
            throw new HTTPClientException("IOException", e);

        } catch (JSONException e) {
            throw new HTTPClientException("JSONException", e);

        }

        return result;
    }

    protected abstract String getQueryString();
}
