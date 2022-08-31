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
    /**
     * read client by id
     * @param id bill's id
     * @return bill by id
     * @throws ReadBillException when bill does not exist
     * **/
    Bill read(Integer id) throws ReadBillException;
    /**
     * update cards
     * @param cardFrom sender's card
     * @param cardTo recipient's card
     * @param bill card's bill
     * @throws UpdateCardException when wrong card
     * @throws NotEnoughException when do not enough money on card
     *  **/
    void updateCard(Card cardFrom, Card cardTo,Bill bill) throws UpdateCardException, NotEnoughException;
    /**
     * update bill
     * @param bill bill that updating
     * @param card sender's card
     * @param cardTo recipient's card
     * @throws UpdateBillException when wrong data for updating
     * **/
    void updateBill(Bill bill, Card card, Card cardTo) throws UpdateBillException;
    /**
     * get list of bills
     * @param card card that contains bills
     * @return list of bills
     * @throws ReadBillException when bill does not exist
     * **/
    List<Bill> getBills(Card card) throws ReadBillException;

    /**
     * delete bill
     * @param id bill's id
     * @throws DeleteBillException when bill does not exist
     */
    void delete(Integer id) throws DeleteBillException;

    /**
     * get card
     * @param id card's id
     * @return card
     * @throws ReadCardException when card does not exist
     */
    Card fillCard(Integer id) throws ReadCardException;
}
