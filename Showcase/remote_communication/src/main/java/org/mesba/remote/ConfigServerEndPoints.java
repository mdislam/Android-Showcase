package org.mesba.remote;

/**
 * Created by mis on 2/5/2016.
 *
 *
 * // Configuring IP and PORT
 * String requestUrl = "";
 *
 * ConfigServerEndPoints.getInstance().setRemoteHost("li1424-15.members.linode.com");
 * ConfigServerEndPoints.getInstance().setPortNumber(9002);
 * ConfigServerEndPoints.getInstance().setProtocol(Protocol.HTTPS);
 *
 * requestUrl = ConfigServerEndPoints.getInstance().getRequestURL("/login");
 *
 */
public class ConfigServerEndPoints {
    private static ConfigServerEndPoints ourInstance = new ConfigServerEndPoints();
    private String remoteHost;
    private int portNumber;
    private Protocol protocol;

    public static ConfigServerEndPoints getInstance() {
        return ourInstance;
    }

    private ConfigServerEndPoints() {
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        getInstance().remoteHost = remoteHost;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        getInstance().portNumber = portNumber;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public String getRequestURL(String apiName){
        StringBuilder urlBuilder = new StringBuilder();

        switch (getProtocol()){
            case HTTP:
                urlBuilder.append("http://" + getRemoteHost() + ":" + getPortNumber());
                break;
            case HTTPS:
                urlBuilder.append("https://" + getRemoteHost() + ":" + getPortNumber());
                break;
        }

        if(!apiName.isEmpty())
            urlBuilder.append(apiName);

        return urlBuilder.toString();
    }
}
