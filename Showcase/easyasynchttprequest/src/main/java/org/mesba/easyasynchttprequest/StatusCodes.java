package org.mesba.easyasynchttprequest;

/**
 * Created by mis on 11/6/2015.
 * https://en.wikipedia.org/wiki/List_of_HTTP_status_codes
 */
public enum StatusCodes {
    OK (200),
    CREATED (201),
    BAD_REQUEST (400),
    UNAUTHORIZED (401),
    FORBIDDEN (403),
    NOT_FOUND (404),
    CONFLICT (409),
    INTERNAL_SERVER_ERROR (500),
    UNKNOWN_ERROR (520);

    private final int statusCode;

    StatusCodes(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode(){
        return this.statusCode;
    }

    /***** How to use in Code

    StatusCodes code = StatusCodes.OK;
    System.out.println(level.getLevelCode());

    */
}
