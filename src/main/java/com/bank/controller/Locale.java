package com.bank.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Locale extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(req.getParameter("lang")!=null){
            req.getSession().setAttribute("defaultLocale",req.getParameter("lang"));
        }
        String referer = req.getHeader("referer");
        resp.sendRedirect(referer);
    }
}