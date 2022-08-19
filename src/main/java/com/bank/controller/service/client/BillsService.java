package com.bank.controller.service.client;

import com.bank.controller.command.exception.NotEnoughException;
import com.bank.model.entity.Bill;
import com.bank.model.entity.Card;
import com.bank.model.exception.bill.DeleteBillException;
import com.bank.model.exception.bill.ReadBillException;
import com.bank.model.exception.bill.UpdateBillException;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.card.UpdateCardException;
import java.util.List;

public interface BillsService {
    Bill read(Integer id) throws ReadBillException;
    void updateCard(Card card,Bill bill) throws UpdateCardException, NotEnoughException;
    void updateBill(Bill bill, Card card) throws UpdateBillException;
    List<Bill> getBills(Card card) throws ReadBillException;

    void delete(Integer id) throws DeleteBillException;
    Card fillCard(Integer id) throws ReadCardException;
}
