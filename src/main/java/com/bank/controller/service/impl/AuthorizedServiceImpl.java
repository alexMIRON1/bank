package com.bank.controller.service.impl;

import com.bank.controller.command.exception.ClientBannedException;
import com.bank.controller.command.exception.WrongPasswordException;
import com.bank.controller.service.AuthorizedService;
import com.bank.model.dao.ClientDao;
import com.bank.model.entity.Client;
import com.bank.model.entity.ClientStatus;
import com.bank.model.entity.Role;
import com.bank.model.exception.client.CreateClientException;
import com.bank.model.exception.client.ReadClientException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class AuthorizedServiceImpl implements AuthorizedService {
    private static final Logger LOG = LogManager.getLogger(AuthorizedServiceImpl.class);
    private final ClientDao clientDao;
    public AuthorizedServiceImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public void create(Client  client, String login,String password, String passwordConfirm) throws CreateClientException {
        if(!password.equals(passwordConfirm)){
            LOG.debug("password not equals");
            throw new CreateClientException();
        }
        client.setLogin(login);
        client.setPassword(password);
        client.setClientStatus(ClientStatus.UNBLOCKED);
        client.setRole(Role.CLIENT);
        clientDao.create(client);
    }

    @Override
    public Client get(String login, String password) throws ReadClientException, WrongPasswordException, ClientBannedException {
        Client client = clientDao.getClient(login);
        if(!client.getPassword().equals(password)){
            LOG.debug("wrong password");
            throw new WrongPasswordException();
        }
        if(client.getClientStatus().equals(ClientStatus.BLOCKED)){
            LOG.debug("client " + login + "was banned");
            throw new ClientBannedException();
        }
        return client;
    }
}
