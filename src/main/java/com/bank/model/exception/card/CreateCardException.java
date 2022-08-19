package com.bank.model.exception.card;

public class CreateCardException extends Exception {
    public CreateCardException() {
        super();
    }

    public CreateCardException(Throwable cause) {
        super(cause);
    }

    public CreateCardException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return "card was not inserted, server failed";
    }
}
