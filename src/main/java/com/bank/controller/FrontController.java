package com.bank.controller;

import com.bank.controller.command.Command;
import com.bank.controller.command.CommandContainer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class FrontController extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(FrontController.class);
    private static final String REDIRECT = "redirect:";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req,resp);
    }

    private static void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("Controller starts");
        String path = request.getServletPath();

        Command command = CommandContainer.getInstance().getCommand(path);
        LOG.info("Obtained command" + command);
        String forward = command.execute(request);
        LOG.info("Forward address --> " + forward);

        forward(forward, request, response);
        redirect(forward, response);
    }

    private static void redirect(String forward, HttpServletResponse response)
            throws IOException {
        if(forward.startsWith(REDIRECT)) {
            LOG.info("Redirect working");
            String redirect = forward.substring(REDIRECT.length());
            response.sendRedirect(redirect);
            LOG.info("Controller finished, go to address--> " +redirect);
        }

    }

    private static void forward(String forward, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(!forward.startsWith(REDIRECT)) {
            LOG.info("Forward working");
            request.getRequestDispatcher("/WEB-INF/jsp" + forward).forward(request, response);
            LOG.info("Controller finished, go to address--> " + forward);
        }
    }

}
