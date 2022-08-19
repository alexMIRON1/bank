package com.bank.controller.service.impl;

import com.bank.controller.command.exception.ClientBannedException;
import com.bank.controller.command.exception.WrongPasswordException;
import com.bank.controller.service.LoginService;
import com.bank.model.dao.ClientDao;
import com.bank.model.entity.Client;
import com.bank.model.entity.ClientStatus;
import com.bank.model.exception.client.ReadClientException;

public class LoginServiceImpl implements LoginService {
    private final ClientDao clientDao;

    public LoginServiceImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public Client get(String login, String password) throws ReadClientException, WrongPasswordException, ClientBannedException {
        Client client = clientDao.getClient(login);
        if(!client.getPassword().equals(password))
            throw new WrongPasswordException();
        if(client.getClientStatus().equals(ClientStatus.BLOCKED))
            throw new ClientBannedException();
        return client;
    }
}
