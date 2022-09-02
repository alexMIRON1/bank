package com.bank.controller.service.client;

import com.bank.model.entity.Card;
import com.bank.model.entity.Client;
import com.bank.model.exception.card.CreateCardException;
import com.bank.model.exception.card.ReadCardException;


public interface ReceiveCardService {
    /**
     * create card
     * @param card new card
     * @param client client who owes card
     * @throws CreateCardException when wrong data for creating
     * @throws ReadCardException card does not exist
     */
    void create(Card card, Client client) throws CreateCardException, ReadCardException;

    /**
     * create name card
     * @return new card name
     * @throws ReadCardException when card does not exist
     */
    String getCardLastName() throws ReadCardException;
}
