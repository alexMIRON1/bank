package com.bank.model.exception.bill;

public class DeleteBillException extends Exception {
    public DeleteBillException() {
        super();
    }

    public DeleteBillException(Throwable cause) {
        super(cause);
    }

    public DeleteBillException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return "bill was not delete, bill does not exist";
    }
}
