package com.dzmitryf.catalog.services.impl;

import org.springframework.http.HttpStatus;

/**
 * The class {@link ApiServiceException}
 */
public class ApiServiceException extends Exception {

    private HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;

    public ApiServiceException(String s) {
        super(s);
    }

    public ApiServiceException(String s, HttpStatus statusCode){
        super(s);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
