package org.mesba.easyasynchttprequest;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by mis on 11/8/2015.
 */
public class AsyncHttpRequest extends AsyncTask<String, Void, RequestResponse> {
    private static final String TAG = AsyncHttpRequest.class.getSimpleName();

    private IAsyncResponse _listener;
    private HashMap<String, String> _params;
    private RequestResponse _response;
    private static String _requestMethod;

    private Context _context;
    private ProgressDialog _progressDialog;
    private boolean isShowProgressDialog = true;

    public AsyncHttpRequest() {
        isShowProgressDialog = false;
        _response = new RequestResponse();
        _params = new HashMap<>();
    }

    public AsyncHttpRequest(Context context) {
        _context = context;
        _response = new RequestResponse();
        _params = new HashMap<>();
    }

    public void setProgressDialog(boolean show){
        isShowProgressDialog = show;
    }

    // POST
    public void asyncPost(String requestUrl, HashMap<String, String> params, IAsyncResponse listener){
        this._params = params;
        this._listener = listener;
        this._requestMethod = RequestTypes.POST;
        this.execute(requestUrl);
    }

    // GET
    public void asyncGet(String requestUrl, HashMap<String, String> params, IAsyncResponse listener) {
        this._params = params;
        this._listener = listener;
        this._requestMethod = RequestTypes.GET;
        this.execute(requestUrl);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // create and display a new ProgressBarDialog
        if(isShowProgressDialog) {
            _progressDialog = new ProgressDialog(_context);
            _progressDialog.setCancelable(true);
            _progressDialog.setMessage("Loading Data ...");
            _progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            _progressDialog.show();
        }
        else {
            _progressDialog = null;
        }
    }

    @Override
    protected RequestResponse doInBackground(String... data) {
        try {
            URL obj = new URL(data[0]);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod(_requestMethod);

            _response.setResponseCode(con.getResponseCode());
            _response.setHeaders(con.getHeaderFields());

            if (_response.getResponseCode() == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                _response.setResponseResult(response.toString());
            } else {
                System.out.println("GET request not worked");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return _response;
    }

    @Override
    protected void onPostExecute(RequestResponse requestResponse) {
        if(_progressDialog != null){
            _progressDialog.dismiss();
        }

        String valContentType = requestResponse.getHeaders().get("Content-Type").get(0);

        if(valContentType.equals("text/plain")) {
            _listener.onResultsSucceeded(requestResponse.getResponseResult(), requestResponse.getResponseCode(), requestResponse.getHeaders());
        }
        else if (valContentType.equals("application/json")) {
            JSONObject object = AsyncRequestHelper.getJSONObject(requestResponse.getResponseResult());
            _listener.onResultsSucceeded(object, requestResponse.getResponseCode(), requestResponse.getHeaders());
        }
    }
}
