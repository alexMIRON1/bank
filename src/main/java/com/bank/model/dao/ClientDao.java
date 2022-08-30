package com.bank.model.dao;

import com.bank.model.entity.Client;
import com.bank.model.exception.client.CreateClientException;
import com.bank.model.exception.client.DeleteClientException;
import com.bank.model.exception.client.ReadClientException;
import com.bank.model.exception.client.UpdateClientException;

import java.sql.Connection;
import java.util.List;

/**
 * interface Client Dao
 * contains CRUD methods and
 * few useful methods to manipulate client's table
 * */
public interface ClientDao extends Dao {


    /**
     * creates client in the database
     * @param client the client need to insert to database
     * @return inserted client
     * @throws CreateClientException in case when client was not inserted
     * */
    Client create(Client client) throws CreateClientException;

    /**
     * reads client by id in the database
     * @param id the client's id
     * @return client
     * @throws ReadClientException in case when client was not found
     * */
    Client read(Integer id) throws ReadClientException;

    /**
     * updates client in the database
     * @param client the client need to update to database
     * @return updated client
     * @throws UpdateClientException in case when client was not updated
     * */
    Client update(Client client) throws UpdateClientException;

    /**
     * deletes client by id in the database
     * @param id the client's id
     * @return deleted client's id
     * @throws DeleteClientException in case when client was not deleted
     * */
    Integer delete(Integer id) throws DeleteClientException;

    /**
     * gets client by login in the database
     * @param login the login
     * @return client the client
     * @throws ReadClientException in case when provided wrong login
     * */
    Client getClient(String login) throws ReadClientException;

    /**
     * gets list of all clients (role eq client)
     * @return list of clients
     * @throws ReadClientException in case when clients was not read
     * */
    List<Client> getClients(int start,int end) throws ReadClientException;
    /**
     * gets amount of records
     * @return amount of records
     * */
    int getNoOfRecords();

}
