package org.mesba.easyasynchttprequest;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mis on 11/6/2015.
 */
public abstract class AsyncRequestHelper {
    /**
     * @param connection object; note: before calling this function,
     *  ensure that the connection is already be open,
     *  and any writes to the connection's output stream should have already been completed.
     * @return String containing the body of the connection response or
     *  null if the input stream could not be read correctly
     */
    public static String readHttpInputStreamToString(HttpURLConnection connection) throws IOException {
        String result = null;
        StringBuffer sb = new StringBuffer();
        InputStream is = null;

        try {
            is = new BufferedInputStream(connection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            result = sb.toString();
        }
        catch (Exception e) {
            // TODO need to check this code future
            is = new BufferedInputStream(connection.getErrorStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            result = sb.toString();
        }
        finally {
            if (is != null) {
                try {
                    is.close();
                }
                catch (IOException e) {
                }
            }
        }

        return result;
    }

    public static String getQuery(HashMap<String, String> map) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, String> entry: map.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    public static JSONObject getJSONObject(String value){
        JSONObject object = new JSONObject();
        try {
            JSONArray array = new JSONArray(value);
            object.put("response", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }
}
