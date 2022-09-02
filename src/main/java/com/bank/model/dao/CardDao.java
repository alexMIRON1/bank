package com.bank.model.dao;

import com.bank.model.entity.Card;
import com.bank.model.entity.Client;
import com.bank.model.exception.card.CreateCardException;
import com.bank.model.exception.card.DeleteCardException;
import com.bank.model.exception.card.ReadCardException;
import com.bank.model.exception.card.UpdateCardException;

import java.util.List;

/**
 * interface Card Dao
 * contains CRUD methods and
 * few useful methods to manipulate card's table
 * */
public interface CardDao extends Dao {

    /**
     * creates card in the database
     * @param card the card need to insert to database
     * @return inserted card
     * @throws CreateCardException in case when card was not inserted
     * */
    Card create(Card card) throws CreateCardException;

    /**
     * reads card by id in the database
     * @param id the card's id
     * @return card
     * @throws ReadCardException in case when card was not found
     * */
    Card read(Integer id) throws ReadCardException;

    /**
     * updates card in the database
     * @param card the card need to update to database
     * @return updated card
     * @throws UpdateCardException in case when card was not updated
     * */
    Card update(Card card) throws UpdateCardException;
    /**
     * updated cards in databes
     * @param from sender's card
     * @param to recipient's card
     * @return updated list cards
     * @throws UpdateCardException in case where something card was not updated
     * **/
    List<Card> transferCard(Card from, Card to) throws  UpdateCardException;

    /**
     * deletes card by id in the database
     * @param id the card's id
     * @return deleted card's id
     * @throws DeleteCardException in case when card was not deleted
     * */
    Integer delete(Integer id) throws DeleteCardException;

    /**
     * gets client's cards
     * @param client the client
     * @return list of client's cards
     * @throws ReadCardException in case when cards was not read
     * */
    List<Card> getCards(Client client) throws ReadCardException;

    /**
     * gets client's cards on each page
     * @param client the client
     * @return list of client's on each page
     * @throws ReadCardException in case when cards was not read
     * */
    List<Card> getCardsOnPage(Client client, int start, int end) throws ReadCardException;


    /**
     * gets amount of records
     * @return amount of records
     * */

    int getNoOfRecords();

    /**
     * gets client's cards sorted by id
     * @param client the client
     * @param start the start page
     * @param end the end page
     * @return list of client's cards sorted by id
     * @throws ReadCardException in case when cards was not read
     * */
    List<Card> getCardsSortedById(Client client,int start, int end) throws ReadCardException;

    /**
     * gets client's cards sorted by name
     * @param client the client
     * @param start the start page
     * @param end the end page
     * @return list of client's cards sorted by name
     * @throws ReadCardException in case when cards was not read
     * */
    List<Card> getCardsSortedByName(Client client, int start, int end) throws ReadCardException;

    /**
     * gets client's cards sorted by balance
     * @param client the client
     * @param start the start page
     * @param end the ned page
     * @return list of client's cards sorted by balance
     * @throws ReadCardException in case when cards was not read
     * */
    List<Card> getCardsSortedByBalance(Client client, int start,int end) throws ReadCardException;

    String getLastCardName() throws ReadCardException;

    /**
     * gets cards need to unlock
     * @return the list of cards where status eq 'ready to unblock'
     * */
    List<Card> getCardsToUnblock(int start, int end) throws ReadCardException;

}
