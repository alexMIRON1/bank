package com.bank.controller.service.client;

import com.bank.model.entity.Card;
import com.bank.model.entity.Client;
import com.bank.model.exception.card.CreateCardException;
import com.bank.model.exception.card.ReadCardException;

import java.sql.SQLException;

public interface ReceiveCardService {
    void create(Card card, Client client) throws CreateCardException, ReadCardException;
    String getCardLastName() throws ReadCardException;
}
