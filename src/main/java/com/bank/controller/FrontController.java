package com.bank.controller;

import com.bank.controller.command.Command;
import com.bank.controller.command.CommandContainer;
import com.bank.controller.exception.WrongLinkException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/")
public class FrontController extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(FrontController.class);
    private static final String REDIRECT = "redirect:";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        process(req,resp);
    }

    private static void process(HttpServletRequest request, HttpServletResponse response) {
        LOG.info("Controller starts");
        String path = request.getServletPath();

        Command command = CommandContainer.getInstance().getCommand(path);
        LOG.info("Obtained command" + command);
        String forward = command.execute(request);
        LOG.info("Forward address --> " + forward);

        forward(forward, request, response);
        redirect(forward, response);
    }

    private static void redirect(String forward, HttpServletResponse response){
        try {
            if(forward.startsWith(REDIRECT)) {
                LOG.info("Redirect working");
                String redirect = forward.substring(REDIRECT.length());
                response.sendRedirect(redirect);
                LOG.info("Controller finished, go to address--> " +redirect);
            }
        }catch (IOException e){
            throw new WrongLinkException("Wrong link");
        }
    }

    private static void forward(String forward, HttpServletRequest request, HttpServletResponse response){
        try{
            if(!forward.startsWith(REDIRECT)) {
                LOG.info("Forward working");
                request.getRequestDispatcher(new StringBuilder("/WEB-INF/jsp").append(forward).toString()).forward(request, response);
                LOG.info("Controller finished, go to address--> " + forward);
            }
        }catch (ServletException | IOException e){
            throw new WrongLinkException("Wrong link");
        }
    }
}
