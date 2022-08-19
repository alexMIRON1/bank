package com.bank.controller.service.client;

import com.bank.model.entity.Bill;
import com.bank.model.entity.Card;
import com.bank.model.entity.Client;
import com.bank.model.entity.Page;
import com.bank.model.exception.bill.ReadBillException;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.client.ReadClientException;

import java.sql.SQLException;
import java.util.List;

public interface PaymentsPageService {
    Card read(Integer id) throws ReadCardException;
    List<Bill> getSortedBills(String sort, Card card,Page page) throws ReadBillException;
    int getNoOfRecords();
    Client fillClient(Integer id) throws ReadClientException;
    Card fillCard(Integer id) throws ReadCardException;
}
