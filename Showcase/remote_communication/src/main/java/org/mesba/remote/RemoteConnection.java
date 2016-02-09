package org.mesba.remote;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by mesba on 2/5/2016.
 *
 * ======
 * Usage:
 * ======
 *
 * RemoteConnection.setRequestType(RequestTypes.POST);
 * RemoteConnection.setProtocol(Protocol.HTTPS);
 * InputStream caInput = new BufferedInputStream(certificate);
 * RemoteConnection.setInputStream(caInput);
 *
 * Response response = new Response();
 * response = RemoteConnection.request(urlString, paramHashMap);
 *
 * NOTE 1:
 * --------
 * // the urlString is a String which should contain the URL with API
 * // for example:
 * urlString = "https://li1424-15.members.linode.com:9002/login

 * // You can use the ConfigureServerEndPoints.java class to generate a URL string with API
 * // Please see the usage section in the header of ConfigureServerEndPoints.java file
 *
 * NOTE 2:
 * --------
 * // the paramHashMap is a HashMap<String, String> which holds the (key, value) pair for the POST request
 * // for example, the /login api expects username and password parameter with the URL
 *
 * HashMap<String, String> paramHashMap = new HashMap<>();
 * paramHashMap.put("email", emailAddress);
 * paramHashMap.put("password", pass);
 *
 * // after creating the URL string and the parameter hashmap, pass these two to the request() method
 * // And, in return get a Response object
 * // So,
 *
 * response = RemoteConnection.request(urlString, paramHashMap);
 *
 */


public abstract class RemoteConnection {
    private static final String TAG = RemoteConnection.class.getSimpleName();
    private static final int HTTP_TOKEN_EXPIRE = 498;
    private static final int READ_TIMEOUT = 10000;
    private static final int CONNECT_TIMEOUT = 15000;

    private static InputStream _inputStream = null;
    private static RequestTypes _requestType;
    private static Protocol _protocol;

    /*
    Setters and Getters
     */

    public static RequestTypes getRequestType() {
        return _requestType;
    }

    public static void setRequestType(RequestTypes _requestType) {
        RemoteConnection._requestType = _requestType;
    }

    public static Protocol getProtocol() {
        return _protocol;
    }

    public static void setProtocol(Protocol _protocol) {
        RemoteConnection._protocol = _protocol;
    }

    public static void setInputStream(InputStream _inputStream) {
        RemoteConnection._inputStream = _inputStream;
    }

    public static InputStream getInputStream() {
        return _inputStream;
    }

    /*
        Class Methods
         */

    /**
     *
     * @param url
     * @param _params
     * @return
     */
    public static Response request(String url, HashMap<String, String> _params){
        String _responseRes = "";
        int _responseCode = 0;
        Map<String, List<String>> _headerMap = new HashMap<>();
        Response _response = new Response();

        switch (getProtocol()){
            case HTTP:
                HttpURLConnection httpURLConnection = setUpNonSecureConnection(url);

                if(httpURLConnection != null) {

                    try {
                        httpURLConnection.setRequestMethod(getRequestType().getRequestTypeValue());

                        //for POST method we need to write the parameters to the connection after we have opened the connection
                        if(getRequestType().getRequestTypeValue() == RequestTypes.POST.getRequestTypeValue()) {
                            configureURLConnection(httpURLConnection);
                            writeParameters(httpURLConnection, _params);
                            httpURLConnection.connect();
                        }

                        _responseCode = httpURLConnection.getResponseCode();
                        _headerMap = httpURLConnection.getHeaderFields();

                        if (_responseCode == HttpURLConnection.HTTP_OK) { // success
                            _responseRes = readHttpInputStreamToString(httpURLConnection);
                            System.out.println("Response from Server: " + _responseRes);
                        } else if (_responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                            System.out.println("HTTP Internal Error");
                            _responseRes = "HTTP Internal Error!!";
                        } else if (_responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                            System.out.println("HTTP BAD REQUEST");
                            _responseRes = "HTTP BAD REQUEST!!";
                        } else if (_responseCode == HTTP_TOKEN_EXPIRE) {
                            System.out.println("HTTP_TOKEN_EXPIRE");
                            _responseRes = "HTTP TOKEN EXPIRE!! Please re-log In!";
                        } else {
                            System.out.println("GET request not worked");
                            _responseRes = " Response Code: " + _responseCode;
                        }

                    } catch (IOException e) {
                        System.out.println("GET :" + e.getMessage());
                        _responseRes = e.getMessage();
                        e.printStackTrace();
                    }
                }
                else _responseRes = "Connection can't be established";

                break;
            case HTTPS:
                HttpsURLConnection httpsURLConnection = setUpSecureConnection(url);

                if (httpsURLConnection != null) {
                    try {
                        httpsURLConnection.setRequestMethod(getRequestType().getRequestTypeValue());

                        //for POST method we need to write the parameters to the connection after we have opened the connection
                        if(getRequestType().getRequestTypeValue() == RequestTypes.POST.getRequestTypeValue()) {
                            configureURLConnection(httpsURLConnection);
                            writeParameters(httpsURLConnection, _params);
                            httpsURLConnection.connect();
                        }

                        _responseCode = httpsURLConnection.getResponseCode();
                        _headerMap = httpsURLConnection.getHeaderFields();

                        if (_responseCode == HttpURLConnection.HTTP_OK) { // success
                            _responseRes = readHttpInputStreamToString(httpsURLConnection);
                            System.out.println("Response from Server: " + _responseRes);
                        } else if (_responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                            System.out.println("HTTP Internal Error");
                            _responseRes = "HTTP Internal Error!!";
                        } else if (_responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                            System.out.println("HTTP BAD REQUEST");
                            _responseRes = "HTTP BAD REQUEST!!";
                        } else if (_responseCode == HTTP_TOKEN_EXPIRE) {
                            System.out.println("HTTP_TOKEN_EXPIRE");
                            _responseRes = "HTTP TOKEN EXPIRE!! Please re-log In!";
                        } else {
                            System.out.println("GET request not worked");
                            _responseRes = " Response Code: " + _responseCode;
                        }

                    } catch (IOException e) {
                        System.out.println("GET :" + e.getMessage());
                        _responseRes = e.getMessage();
                        e.printStackTrace();
                    }
                }
                else _responseRes = "Connection can't be established";

                break;
        }

        _response.setResponseCode(_responseCode);
        _response.setHeaderMap(_headerMap);
        _response.setResponseResult(_responseRes);

        return _response;
    }

    /**
     * In POST method we need to write the parameters to the connection after we have opened the connection
     *
     * @param connection
     * @param params
     */
    private static void writeParameters(URLConnection connection, HashMap<String, String> params){
        try {
            OutputStream os = connection.getOutputStream();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            writer.write(getQuery(params));
            writer.flush();
            writer.close();
            os.close();

            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param connection object; note: before calling this function,
     *  ensure that the connection is already be open,
     *  and any writes to the connection's output stream should have already been completed.
     * @return String containing the body of the connection response or
     *  null if the input stream could not be read correctly
     */
    private static String readHttpInputStreamToString(URLConnection connection) throws IOException {
        String result = "";
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        InputStream is = null;

        try {
            is = new BufferedInputStream(connection.getInputStream());
        }
        catch (Exception e) {
            System.out.println(TAG + ": Error reading InputStream");

            if(connection instanceof HttpURLConnection){
                is = new BufferedInputStream(((HttpURLConnection)connection).getErrorStream());
            }
            else if(connection instanceof HttpsURLConnection){
                is = new BufferedInputStream(((HttpsURLConnection)connection).getErrorStream());
            }
        }
        finally {
            br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            result = sb.toString();

            if (is != null) {
                try {
                    is.close();
                }
                catch (IOException e) {
                    System.out.println(TAG + ": Error closing InputStream");
                }
            }
        }

        return result;
    }

    /**
     * Setting Up HTTP Connection
     *
     * @param urlString
     * @return
     */
    private static HttpURLConnection setUpNonSecureConnection(String urlString){
        HttpURLConnection urlConnection = null;

        try {
            URL obj = new URL(urlString);
            urlConnection = (HttpURLConnection) obj.openConnection();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return urlConnection;
    }

    /**
     * Setting Up HTTPS Connection
     *
     * @param urlString
     * @return
     */
    private static HttpsURLConnection setUpSecureConnection(String urlString){
        HttpsURLConnection urlConnection = null;

        try
        {
            // Load CAs from an InputStream
            // (could be from a resource or ByteArrayInputStream or ...)
            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            // The CRT file is put in the res/raw folder
            Certificate ca = cf.generateCertificate(getInputStream());
            System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());

            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            // Create an SSLContext that uses our TrustManager
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);

            URL url = new URL(urlString);
            urlConnection = (HttpsURLConnection)url.openConnection();

            urlConnection.setSSLSocketFactory(context.getSocketFactory());
        }
        catch (Exception ex)
        {
            System.out.println("Failed to establish SSL connection to server: " + ex.toString());
        }

        return urlConnection;
    }

    /**
     *
     *
     * @param connection
     */
    private static void configureURLConnection(URLConnection connection){
        connection.setReadTimeout(READ_TIMEOUT);
        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setDoInput(true);
        connection.setDoOutput(true);
    }

    /**
     *
     *
     * @param map
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String getQuery(HashMap<String, String> map) throws UnsupportedEncodingException
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
}
