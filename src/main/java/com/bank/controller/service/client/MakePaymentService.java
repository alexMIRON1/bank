package com.bank.controller.service.client;

import com.bank.controller.command.exception.CardBannedException;
import com.bank.model.entity.Bill;
import com.bank.model.entity.Card;
import com.bank.model.exception.bill.CreateBillException;

import java.math.BigDecimal;

public interface MakePaymentService{
    /**
     * create bill
     * @param bill new bill
     * @param sum set sum bill
     * @param card set card bill
     * @return new bill
     * @throws CreateBillException when wrong data for creating bill
     * @throws CardBannedException when card is banned
     */
    Bill create(Bill bill, BigDecimal sum, Card card) throws CreateBillException, CardBannedException;
}
