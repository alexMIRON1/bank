package com.bank.controller.command.impl;

import com.bank.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class RegisterPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/register.jsp";
    }
}
