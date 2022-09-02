package com.bank.controller.service.admin;

import com.bank.model.entity.Card;
import com.bank.model.entity.Client;
import com.bank.model.entity.Page;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.client.ReadClientException;

import java.util.List;

public interface AdminPageService {
    /**
     * list of clients
     * @param page number page
     * @return get clients
     * @throws ReadClientException when client does not exist
     */
    List<Client> getClients(Page page) throws ReadClientException;

    /**
     * list of cards
     * @param page number page
     * @return get cards
     * @throws ReadCardException when card does not exist
     */
    List<Card> getCards(Page page) throws ReadCardException;
    /**
     * get client
     * @param id client's id
     * @return client
     * @throws ReadClientException when client does not exist
     */
    Client fillClient(Integer id) throws ReadClientException;
    /**
     * get records for client which do not fit on page
     * @return number of no records for client
     */
    int getNoOfRecords();
    /**
     * get records for cards which do not fit on page
     * @return number of no records for cards
     */
    int getNoOfRecordsCard();
}
