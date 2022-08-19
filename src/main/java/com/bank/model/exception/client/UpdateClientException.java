package com.bank.model.exception.client;

public class UpdateClientException extends Exception {

    public UpdateClientException() {
        super();
    }

    public UpdateClientException(Throwable cause) {
        super(cause);
    }

    public UpdateClientException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return "client was not updated, something wrong with server or invalid input data";
    }
}
