package com.bank.controller.command.impl.error;

import com.bank.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class AccessDenyPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/errors/accessDeny.jsp";
    }
}
