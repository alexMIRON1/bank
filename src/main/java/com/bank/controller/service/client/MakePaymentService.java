package com.bank.controller.service.client;

import com.bank.controller.command.exception.CardBannedException;
import com.bank.model.entity.Bill;
import com.bank.model.entity.Card;
import com.bank.model.exception.bill.CreateBillException;

public interface MakePaymentService{
    Bill create(Bill bill, Integer sum, Card card) throws CreateBillException, CardBannedException;
}
