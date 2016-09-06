package org.mesba.smartasync;

import java.util.List;
import java.util.Map;

/**
 * Created by mis on 9/1/2015.
 */
public interface IAsyncResponse {
    void onResultsFailed(String response, int statusCode, Map<String, List<String>> header);
    void onResultsSucceeded(String response, int statusCode, Map<String, List<String>> header);
}
