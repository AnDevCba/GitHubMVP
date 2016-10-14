package com.andevcba.githubmvp.data.model;

/**
 * Model class representing an error response.
 *
 * @author lucas.nobile
 */
public class ErrorResponse {

    private int code;
    private String message;

    public ErrorResponse() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
