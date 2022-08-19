package com.bank.controller.service.client;

import com.bank.model.entity.Card;
import com.bank.model.entity.Client;
import com.bank.model.entity.Page;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.client.ReadClientException;

import java.sql.SQLException;
import java.util.List;

public interface HomePageService {
    List<Card> sort(Client client,String sort, Page pageList) throws ReadCardException;
    Client fillClient(Integer id) throws ReadClientException;
    int getNoOfRecords();
}
