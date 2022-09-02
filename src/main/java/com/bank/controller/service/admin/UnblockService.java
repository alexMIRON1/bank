package com.bank.controller.service.admin;

import com.bank.model.entity.Card;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.card.UpdateCardException;


public interface UnblockService {
    /**
     * get card
     * @param id card's id
     * @return card
     * @throws ReadCardException when card does not exist
     */
    Card read(Integer id) throws ReadCardException;

    /**
     * updating card
     * @param card for updating
     * @throws UpdateCardException when wrong data for updating
     */
    void update(Card card) throws UpdateCardException;
}
