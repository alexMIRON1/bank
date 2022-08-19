package com.bank.model.exception.bill;

public class ReadBillException extends Exception {
    public ReadBillException() {
        super();
    }

    public ReadBillException(Throwable cause) {
        super(cause);
    }

    public ReadBillException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return "bill was not read, bill does not exist";
    }
}
