package com.bank.controller.command.impl.client;

import com.bank.controller.command.Command;
import com.bank.controller.service.client.CardsService;
import com.bank.controller.service.client.HomePageService;
import com.bank.model.entity.Card;
import com.bank.model.entity.Client;
import com.bank.model.entity.Page;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.client.ReadClientException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

public class HomePageCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(HomePageCommand.class);
    private final HomePageService homePageService;

    public HomePageCommand(HomePageService homePageService) {
        this.homePageService = homePageService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Client client = (Client) request.getSession().getAttribute("client");
        String sort = Objects.isNull(request.getParameter("sort")) ? "" : request.getParameter("sort");
        String locale = String.valueOf(request.getSession().getAttribute("locale"));
        try {
            Page page = new Page();
            page.setNumberPage(1);
            page.setRecords(3);
            if(request.getParameter("page")!=null){
                page.setNumberPage(Integer.parseInt(request.getParameter("page")));
                LOG.info("page = " + page.getNumber());
            }
            Client fullClient = homePageService.fillClient(client.getId());
            List<Card> cards = getCards(sort, fullClient,page);
            int noOfRecords = homePageService.getNoOfRecords();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / page.getRecords());
            request.getSession().setAttribute("page",page);
            request.getSession().setAttribute("cards", cards);
            request.getSession().setAttribute("noOfPages", noOfPages);
        } catch (ReadCardException | NullPointerException | ReadClientException e) {
            // fail to obtain cards | NullPointerException when client is null. Fix it xD
            LOG.debug("fail to obtain cards");
            return "/error.jsp";
        }
        return "/home.jsp";
    }
    /**
     * gets cards by sort param
     * @param sort the string-param
     * @param client the client
     * @throws ReadCardException in case when cards was not read
     * @throws NullPointerException in case when client is null
     * */
    private List<Card> getCards(String sort, Client client,Page page) throws ReadCardException, NullPointerException, ReadClientException{
        List<Card> cards;
        cards = homePageService.sort(client,sort,page);
        for (Card card: cards) {
            card.setClient(homePageService.fillClient(card.getClient().getId()));
        }
        return cards;
    }
}
