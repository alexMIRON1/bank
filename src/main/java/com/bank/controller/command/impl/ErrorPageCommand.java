package com.bank.controller.command.impl;

import com.bank.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class ErrorPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/error.jsp";
    }
}
