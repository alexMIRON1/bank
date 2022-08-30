package com.bank.controller.service.admin;

import com.bank.controller.command.exception.ClientBannedException;
import com.bank.model.entity.Card;
import com.bank.model.entity.Client;
import com.bank.model.entity.Page;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.client.ReadClientException;

import java.sql.SQLException;
import java.util.List;

public interface AdminPageService {
    List<Client> getClients(Page page) throws ReadClientException;
    List<Card> getCards(Page page) throws ReadCardException;
    Client fillClient(Integer id) throws ReadClientException;

    int getNoOfRecords();
    int getNoOfRecordsCard();
}
