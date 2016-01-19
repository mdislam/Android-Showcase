package org.mesba.easyasynchttprequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mis on 11/6/2015.
 */
public class RequestResponse {
    private String _responseResult;
    private int _responseCode;
    private Map<String, List<String>> _headers;

    public RequestResponse() {
        _responseResult = "";
        _responseCode = -1;
        _headers = new HashMap<>();
    }

    public String getResponseResult() {
        return _responseResult;
    }

    public void setResponseResult(String responseResult) {
        _responseResult = responseResult;
    }

    public int getResponseCode() {
        return _responseCode;
    }

    public void setResponseCode(int responseCode) {
        _responseCode = responseCode;
    }

    public Map<String, List<String>> getHeaders() {
        return _headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        _headers = headers;
    }
}
