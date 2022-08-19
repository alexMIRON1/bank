package com.bank.model.exception;

public class DaoFactoryException extends RuntimeException {
    public DaoFactoryException() {
        super();
    }
    public DaoFactoryException(Throwable cause) {
        super(cause);
    }
    public DaoFactoryException(String message) {
        super(message);
    }
    public DaoFactoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
