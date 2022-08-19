package com.bank.model.exception.client;

public class ReadClientException extends Exception {

    public ReadClientException() {
        super();
    }

    public ReadClientException(Throwable cause) {
        super(cause);
    }

    public ReadClientException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return "Client does not exist";
    }
}
