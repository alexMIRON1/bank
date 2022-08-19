package com.bank.model.exception.card;

public class UpdateCardException extends Exception {
    public UpdateCardException() {
        super();
    }

    public UpdateCardException(Throwable cause) {
        super(cause);
    }

    public UpdateCardException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return "Card was not update something wrong";
    }
}
