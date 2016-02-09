package org.mesba.remote;

/**
 * Created by mis on 2/7/2016.
 */
public enum RequestTypes {
    POST ("POST"),
    GET ("GET"),
    PUT ("PUT"),
    DELETE ("DELETE");

    private final String requestType;

    RequestTypes(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestTypeValue(){
        return this.requestType;
    }

    /***** How to use in Code

     RequestTypes type = RequestTypes.POST;
     System.out.println(type.getRequestTypeValue());

     */
}
