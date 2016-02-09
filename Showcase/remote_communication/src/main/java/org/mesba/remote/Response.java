package org.mesba.remote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mis on 10/27/2015.
 */
public class Response {
    private String responseResult;
    private int responseCode;
    private Map<String, List<String>> headerMap;

    public Response() {
        responseResult = "";
        headerMap = new HashMap<>();
        responseCode = 0;
    }

    public String getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(String responseResult) {
        this.responseResult = responseResult;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public Map<String, List<String>> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(Map<String, List<String>> headerMap) {
        this.headerMap = headerMap;
    }
}
