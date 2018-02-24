package com.dzmitryf.catalog.controllers;

public class ErrorResponse {

    private int statusCode;

    private String message;

    public ErrorResponse(){}

    public ErrorResponse(int statusCode, String message){
        setStatusCode(statusCode);
        setMessage(message);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
