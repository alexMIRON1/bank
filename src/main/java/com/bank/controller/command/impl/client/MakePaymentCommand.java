package com.bank.controller.command.impl.client;

import com.bank.controller.command.Command;
import com.bank.controller.command.exception.CardBannedException;
import com.bank.controller.service.client.MakePaymentService;
import com.bank.model.entity.*;
import com.bank.model.exception.bill.CreateBillException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class MakePaymentCommand implements Command {
    private final MakePaymentService makePaymentService;
    private static final Logger LOG = LogManager.getLogger(MakePaymentCommand.class);

    public MakePaymentCommand(MakePaymentService registerService) {
        this.makePaymentService = registerService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Page page = (Page)request.getSession().getAttribute("page");
        BigDecimal sum;
        if(request.getParameter("sum").equals("")){
            sum = BigDecimal.ZERO;
        }else {
            sum = BigDecimal.valueOf(Double.parseDouble(request.getParameter("sum")));
        }
        Card card = (Card) request.getSession().getAttribute("currentCard");
        try {
            Bill bill = new Bill();
            makePaymentService.create(bill,sum,card);
            LOG.debug("payment was successfully created");
        } catch (CreateBillException e) {
            // such bill already exist
            LOG.debug("fail to create bill");
            return "/payments.jsp";
        } catch (CardBannedException e) {
            LOG.debug("fail to create bill--> card is banned");
            return "/errors/cardBanned.jsp";
        }
        return "redirect:/bank/payments?page=" + page.getNumber() + "&card=" + card.getId();
    }
}
