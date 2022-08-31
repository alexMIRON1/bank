package com.bank.controller.service.client;

import com.bank.model.entity.Card;
import com.bank.model.entity.Client;
import com.bank.model.entity.Page;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.client.ReadClientException;
import java.util.List;

public interface HomePageService {
    /**
     * get list cards
     * @param client client who has cards
     * @param sort type of sorting
     * @param pageList number page
     * @return list of cards on one page
     * @throws ReadCardException when cards  do not exist
     */
    List<Card> sort(Client client,String sort, Page pageList) throws ReadCardException;

    /**
     * get client
     * @param id client's id
     * @return client
     * @throws ReadClientException when client does not exist
     */
    Client fillClient(Integer id) throws ReadClientException;

    /**
     * get records which do not fit on page
     * @return number of no records
     */
    int getNoOfRecords();
}
