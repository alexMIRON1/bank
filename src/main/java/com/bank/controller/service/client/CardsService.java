package com.bank.controller.service.client;

import com.bank.model.entity.Card;
import com.bank.model.entity.Client;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.card.UpdateCardException;
import com.bank.model.exception.client.ReadClientException;

import java.sql.SQLException;

public interface CardsService {
    void updateStatus(Card card) throws UpdateCardException ;
    Card read(Integer cardId) throws ReadCardException;
    void updateTopUp(Card card, Integer topUp) throws UpdateCardException;
    void updateCustomName(Card card, String name) throws UpdateCardException;
}
