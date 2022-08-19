package com.bank.model.exception.client;

public class CreateClientException extends Exception {
    public CreateClientException() {
        super();
    }

    public CreateClientException(Throwable cause) {
        super(cause);
    }

    public CreateClientException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return "client was not inserted";
    }
}
