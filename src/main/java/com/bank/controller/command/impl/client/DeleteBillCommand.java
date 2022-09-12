package com.bank.controller.command.impl.client;

import com.bank.controller.command.Command;
import com.bank.controller.service.client.BillsService;
import com.bank.model.entity.Bill;
import com.bank.model.entity.Card;
import com.bank.model.entity.Page;
import com.bank.model.exception.bill.DeleteBillException;
import com.bank.model.exception.bill.ReadBillException;
import com.bank.model.exception.card.ReadCardException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class DeleteBillCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(DeleteBillCommand.class);
    private final BillsService billsService;

    public DeleteBillCommand(BillsService billsService) {
        this.billsService = billsService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Page page = (Page) request.getSession().getAttribute("page");
        int noOfRecords = (int) request.getSession().getAttribute("noOfRecords");
        Integer billId = Integer.parseInt(request.getParameter("bill"));
        try {
            Bill bill = billsService.read(billId);
            Card card = billsService.fillCard(bill.getCard().getId());
            billsService.delete(bill.getId());
            noOfRecords--;
            if(noOfRecords % 3 == 0 && page.getNumber()-1!=0){
                LOG.debug("Bill with id = " + billId + " was successfully delete");
                return "redirect:/bank/payments?page=" + (page.getNumber()-1) + "&card=" + card.getId();
            }
            LOG.debug("Bill with id = " + billId + " was successfully delete");
            return "redirect:/bank/payments?page=" + page.getNumber() + "&card=" + card.getId();
        } catch (DeleteBillException e) {
            LOG.debug("fail to delete bill-->wrong id");
            return "/error.jsp";
        } catch (ReadBillException | ReadCardException e) {
            LOG.debug("fail to obtain bill-->such bill does not exist");
            return "/error.jsp";
        }

    }
}
