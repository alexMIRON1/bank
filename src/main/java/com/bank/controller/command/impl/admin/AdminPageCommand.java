package com.bank.controller.command.impl.admin;

import com.bank.controller.command.Command;
import com.bank.controller.service.admin.AdminPageService;
import com.bank.model.dao.CardDao;
import com.bank.model.dao.ClientDao;
import com.bank.model.dao.impl.DaoEnum;
import com.bank.model.dao.impl.FactoryDao;
import com.bank.model.entity.Card;
import com.bank.model.entity.Client;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.client.ReadClientException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class AdminPageCommand implements Command {
    private final AdminPageService adminPageService;
    private static final Logger LOG = LogManager.getLogger(AdminPageCommand.class);

    public AdminPageCommand(AdminPageService adminPageService) {
        this.adminPageService = adminPageService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        try {
            List<Client> clients = adminPageService.getClients();
            List<Card> cards = adminPageService.getCards();
            for (Card card: cards) {
                card.setClient(adminPageService.fillClient(card.getClient().getId()));
            }
            request.setAttribute("clients", clients);
            request.setAttribute("unlockCards", cards);
        } catch (ReadClientException e) {
            // clients are not read. error in the dao
            LOG.debug("clients are not obtained");
            return "/error.jsp";
        } catch (ReadCardException e) {
            // cards are not read. error in the dao
            LOG.debug("cards are not obtained");
            return "/error.jsp";
        }
        return "/admin.jsp";
    }
}
