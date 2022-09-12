package com.bank.controller.command.impl.error;

import com.bank.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class ClientBannedPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/errors/clientBanned.jsp";
    }
}
