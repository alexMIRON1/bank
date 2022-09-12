package com.bank.controller.command.impl.error;

import com.bank.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class ErrorPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/errors/error.jsp";
    }
}
