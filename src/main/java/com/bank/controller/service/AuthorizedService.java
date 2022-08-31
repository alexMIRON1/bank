package com.bank.controller.service;

import com.bank.controller.command.exception.ClientBannedException;
import com.bank.controller.command.exception.WrongPasswordException;
import com.bank.model.entity.Client;
import com.bank.model.exception.client.CreateClientException;
import com.bank.model.exception.client.ReadClientException;

public interface AuthorizedService {
    /**
     * create client with some params
     * @param client the client
     * @param login client's login
     * @param password client's password
     * @param passwordConfirm confirming password
     * @throws CreateClientException when some params are invalid
     * **/
    void create(Client client ,String login, String password, String passwordConfirm) throws CreateClientException;
    /**
     * get client by login
     * @param login client's login
     * @param password client's password
     * @return client by login
     * @throws ReadClientException when client doesn't exist
     * @throws WrongPasswordException when client input wrong password
     * @throws ClientBannedException when client is banned
     * **/
    Client get(String login, String password) throws ReadClientException, WrongPasswordException, ClientBannedException;
}
