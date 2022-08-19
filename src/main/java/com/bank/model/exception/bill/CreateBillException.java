package com.bank.model.exception.bill;

public class CreateBillException extends Exception {
    public CreateBillException() {
        super();
    }

    public CreateBillException(Throwable cause) {
        super(cause);
    }

    public CreateBillException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return "bill was not inserted, wrong data";
    }
}
