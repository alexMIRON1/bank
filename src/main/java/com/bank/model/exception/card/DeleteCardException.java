package com.bank.model.exception.card;

public class DeleteCardException extends Exception {
    public DeleteCardException() {
        super();
    }

    public DeleteCardException(Throwable cause) {
        super(cause);
    }

    public DeleteCardException(String message, Throwable cause) {
        super(message, cause);
    }
}
