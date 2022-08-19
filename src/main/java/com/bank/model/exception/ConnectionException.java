package com.bank.model.exception;

public class ConnectionException extends RuntimeException {
    public ConnectionException() {
        super();
    }
    public ConnectionException(Throwable cause) {
        super(cause);
    }
    public ConnectionException(String message) {
        super(message);
    }
    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return "Failed connection";
    }
}
