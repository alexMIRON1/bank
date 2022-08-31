package com.bank.controller.service.client;

import com.bank.model.entity.Bill;
import com.bank.model.entity.Card;
import com.bank.model.entity.Client;
import com.bank.model.entity.Page;
import com.bank.model.exception.bill.ReadBillException;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.client.ReadClientException;

import java.util.List;

public interface PaymentsPageService {
    /**
     * get card
     * @param id card's id
     * @return card
     * @throws ReadCardException when card does not exist
     */
    Card read(Integer id) throws ReadCardException;

    /**
     * get bills
     * @param sort type of sorting
     * @param card that has bills
     * @param page number of page
     * @return list of bill
     * @throws ReadBillException when bill does not exist
     */
    List<Bill> getSortedBills(String sort, Card card,Page page) throws ReadBillException;

    /**
     * get records which do not fit on page
     * @return number of no records
     */
    int getNoOfRecords();

    /**
     * get client
     * @param id client's id
     * @return client
     * @throws ReadClientException when client does not exist
     */
    Client fillClient(Integer id) throws ReadClientException;

    /**
     * get card
     * @param id card's id
     * @return card
     * @throws ReadCardException when card does not exist
     */
    Card fillCard(Integer id) throws ReadCardException;
}
