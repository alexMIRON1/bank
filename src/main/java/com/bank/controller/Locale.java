package com.bank.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Locale extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String locale = req.getParameter("lang");
        resp.addCookie(new Cookie("defaultLocale", locale));
        String referer = req.getHeader("referer");
        req.getSession().setAttribute("locale", locale);
        resp.sendRedirect(referer);
    }
}