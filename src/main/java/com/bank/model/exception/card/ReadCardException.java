package com.bank.model.exception.card;

public class ReadCardException extends Exception {
    public ReadCardException() {
        super();
    }

    public ReadCardException(Throwable cause) {
        super(cause);
    }

    public ReadCardException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return "card was not found because card doesn't exist";
    }
}
