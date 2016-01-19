package org.mesba.easyasynchttprequest;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by mis on 11/17/2015.
 */
public interface IAsyncResponse {
    void onResultsFailed(String response, int statusCode, Map<String, List<String>> header);
    void onResultsSucceeded(String response, int statusCode, Map<String, List<String>> header);
    void onResultsSucceeded(JSONObject response, int statusCode, Map<String, List<String>> header);
}
