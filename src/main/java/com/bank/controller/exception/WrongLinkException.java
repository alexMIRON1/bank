package com.bank.controller.exception;

public class WrongLinkException extends RuntimeException{
    public  WrongLinkException(){super();}
    public WrongLinkException(String cause){super(cause);}
}
