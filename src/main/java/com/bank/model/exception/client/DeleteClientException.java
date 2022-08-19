package com.bank.model.exception.client;

public class DeleteClientException extends Exception {
    public DeleteClientException() {
        super();
    }

    public DeleteClientException(Throwable cause) {
        super(cause);
    }

    public DeleteClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
