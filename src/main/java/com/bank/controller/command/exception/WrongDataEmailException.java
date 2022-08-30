package com.bank.controller.command.exception;

public class WrongDataEmailException extends RuntimeException{
    public WrongDataEmailException() {super();
    }

    public WrongDataEmailException(String message) {
        super(message);
    }
}
