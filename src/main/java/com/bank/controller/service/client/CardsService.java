package com.bank.controller.service.client;

import com.bank.model.entity.Card;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.card.UpdateCardException;

public interface CardsService {
    /**
     * update status
     * @param card card for update
     * @throws UpdateCardException when wrong data for updating
     */
    void updateStatus(Card card) throws UpdateCardException ;

    /**
     * get card
     * @param cardId card's id
     * @return car
     * @throws ReadCardException when card does not exist
     */
    Card read(Integer cardId) throws ReadCardException;

    /**
     * top-up card
     * @param card card for top-up
     * @param topUp sum of top-up
     * @throws UpdateCardException when wrong data for top-up
     */
    void updateTopUp(Card card, Integer topUp) throws UpdateCardException;

    /**
     * set custom name
     * @param card card for setting custom name
     * @param name new name card
     * @throws UpdateCardException when wrong data for updating
     */
    void updateCustomName(Card card, String name) throws UpdateCardException;
}
