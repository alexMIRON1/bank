package com.bank.controller.command.impl.client;

import com.bank.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/bank/login";
    }
}
