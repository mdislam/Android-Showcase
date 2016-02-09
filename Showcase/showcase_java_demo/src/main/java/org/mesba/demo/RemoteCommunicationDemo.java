package org.mesba.demo;

import org.mesba.remote.ConfigServerEndPoints;
import org.mesba.remote.Protocol;
import org.mesba.remote.RemoteConnection;
import org.mesba.remote.RequestTypes;
import org.mesba.remote.Response;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Created by mis on 2/9/2016.
 */
public class RemoteCommunicationDemo {
    public static void main (String[] args ) {
        String requestUrl = "";
        Response response = new Response();

        // Configuring IP and PORT
        ConfigServerEndPoints.getInstance().setRemoteHost("li1424-15.members.linode.com");
        ConfigServerEndPoints.getInstance().setPortNumber(9002);
        ConfigServerEndPoints.getInstance().setProtocol(Protocol.HTTPS);

        // Generating the URL String
        requestUrl = ConfigServerEndPoints.getInstance().getRequestURL("/login");

        // Preparing parameters for POST request
        HashMap<String, String> params = new HashMap<>();
        params.put("email", "m@nls.fi");
        params.put("password", "123456789");

        RemoteConnection.setRequestType(RequestTypes.POST);
        RemoteConnection.setProtocol(Protocol.HTTPS);

        InputStream caInput = null;
        String certPath = Paths.get(".").toAbsolutePath().normalize().toString() + "\\showcaseclient\\src\\main\\certs\\mygt.crt";
        System.out.println(certPath);
        try {
            caInput = new BufferedInputStream(new FileInputStream(certPath));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        RemoteConnection.setInputStream(caInput);

        // Requesting the server and getting response
        response = RemoteConnection.request(requestUrl, params);

        String token = "N/A";
        String responseResult = response.getResponseResult();
        int statusCode = response.getResponseCode();
        if(statusCode == 200)
            token = response.getHeaderMap().get("Authorization").get(0);

        System.out.println("=================================================================");
        System.out.println("Auth Token: " + token);
        System.out.println("Response Result: " + responseResult);
        System.out.println("Status Code: " + statusCode);
    }
}
