package com.bank.model.exception.bill;

public class UpdateBillException extends Exception {
    public UpdateBillException() {
        super();
    }

    public UpdateBillException(Throwable cause) {
        super(cause);
    }

    public UpdateBillException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return "bill was not update, wrong data";
    }
}
