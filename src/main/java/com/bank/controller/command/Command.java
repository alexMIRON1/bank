package com.bank.controller.command;

import javax.servlet.http.HttpServletRequest;

public interface Command {

    /**
     * Executes command and produces path
     * @param request change condition through request
     * @return path the path to redirect\forward
    */
    String execute(HttpServletRequest request);

}
