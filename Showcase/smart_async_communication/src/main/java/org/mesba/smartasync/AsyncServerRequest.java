package org.mesba.smartasync;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.mesba.remote.Protocol;
import org.mesba.remote.RemoteConnection;
import org.mesba.remote.RequestTypes;
import org.mesba.remote.Response;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mis on 9/9/2015.
 * modified by mesbahul on 27.10.2015
 */
public class AsyncServerRequest extends AsyncTask<String, Void, Response> {
    private static final String TAG = AsyncServerRequest.class.getSimpleName();

    private IAsyncResponse _listener;
    private Map<String, List<String>> _headerMap;
    private HashMap<String, String> _params;
    private Response _response;
    private Protocol _protocol;
    private RequestTypes _requestTypes;
    private int _certResId;

    private Context _context;
    private ProgressDialog _progressDialog;
    private boolean isShowProgressDialog = true;

    public AsyncServerRequest(Context context) {
        _context = context;
        _response = new Response();
        _headerMap = new HashMap<>();
        _params = new HashMap<>();
    }

    public AsyncServerRequest() {
        _response = new Response();
        _headerMap = new HashMap<>();
        _params = new HashMap<>();
    }

    // POST
    public void asyncPost(String requestUrl, HashMap<String, String> params, Protocol protocol, int certResId, IAsyncResponse listener){
        this._params = params;
        this._listener = listener;
        this._requestTypes = RequestTypes.POST;
        this._protocol = protocol;
        this._certResId = certResId;
        this.execute(requestUrl);
    }

    // PUT

    // GET
    public void asyncGet(String requestUrl, HashMap<String, String> params, Protocol protocol, int certResId, IAsyncResponse listener){
        this._params = params;
        this._listener = listener;
        this._requestTypes = RequestTypes.GET;
        this._protocol = protocol;
        this._certResId = certResId;
        this.execute(requestUrl);
    }

    public void setProgressDialog(boolean show){
        isShowProgressDialog = show;
    }

    public Protocol getProtocol() {
        return _protocol;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // create and display a new ProgressBarDialog
        if(isShowProgressDialog) {
            _progressDialog = new ProgressDialog(_context);
            _progressDialog.setCancelable(true);
            _progressDialog.setMessage("Connecting Server ...");
            _progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            _progressDialog.show();
        }
        else {
            _progressDialog = null;
        }
    }

    @Override
    protected Response doInBackground(String... data) {
        InputStream caInput;

        caInput = new BufferedInputStream(_context.getResources().openRawResource(_certResId));

        RemoteConnection.setRequestType(_requestTypes);
        RemoteConnection.setProtocol(_protocol);

        if(_params == null)
            _params = new HashMap<>();

        RemoteConnection.setInputStream(caInput);

        // Requesting the server and getting response
        _response = RemoteConnection.request(data[0], _params);

        return _response;
    }

    @Override
    protected void onPostExecute(Response response) {
        if(_progressDialog != null){
            _progressDialog.dismiss();
        }

        switch (response.getResponseCode()){
            case 200: // SUCCESS
                _listener.onResultsSucceeded(response.getResponseResult(), response.getResponseCode(), response.getHeaderMap());
                break;
            case 400: // BAD REQUEST
                _listener.onResultsFailed(response.getResponseResult(), response.getResponseCode(), response.getHeaderMap());
                break;
            case 403: // FORBIDDEN
                _listener.onResultsFailed(response.getResponseResult() + " Please try again!", response.getResponseCode(), response.getHeaderMap());
                break;
            case 498: // TOKEN EXPIRE
                _listener.onResultsFailed(response.getResponseResult(), response.getResponseCode(), response.getHeaderMap());
                break;
            case 500: // INTERNAL SERVER ERROR
                _listener.onResultsFailed("Internal Server Error. Please report to developers for details.", response.getResponseCode(), response.getHeaderMap());
                break;
            case 0: // No internet or server unreachable
                _listener.onResultsFailed("The request is not completed. Please check your internet connection or contact the developer.", response.getResponseCode(), response.getHeaderMap());
                break;
            default:
                break;
        }
    }

}
