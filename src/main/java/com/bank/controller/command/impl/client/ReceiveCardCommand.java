package com.bank.controller.command.impl.client;

import com.bank.controller.command.Command;
import com.bank.controller.service.client.ReceiveCardService;
import com.bank.model.entity.Card;
import com.bank.model.entity.Client;
import com.bank.model.exception.card.CreateCardException;
import com.bank.model.exception.card.ReadCardException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
public class ReceiveCardCommand implements Command {
    private final ReceiveCardService receiveCardService;
    private static final Logger LOG = LogManager.getLogger(ReceiveCardCommand.class);

    public ReceiveCardCommand(ReceiveCardService receiveCardService) {
        this.receiveCardService = receiveCardService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Client client = (Client) request.getSession().getAttribute("client");
        int noOfPages = (int) request.getSession().getAttribute("noOfPages");
        try {
            Card card = new Card();
            receiveCardService.create(card,client);
        } catch (CreateCardException e) {
            // fail to create card. perhaps such card already exist
            LOG.debug("fail to create card");
            return "/error.jsp";
        } catch (ReadCardException e) {
            // fail to obtain last card name ==> generate card's name.
            LOG.debug("fail to generate card's name");
            return "/error.jsp";
        }
        LOG.debug("card was successfully receive");
        return "redirect:/bank/home?page=" + (noOfPages != 0 ? noOfPages : noOfPages + 1);
    }
}
