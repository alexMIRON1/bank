package com.bank.controller.service.impl;

import com.bank.controller.service.RegisterService;
import com.bank.model.dao.ClientDao;
import com.bank.model.entity.Client;
import com.bank.model.entity.ClientStatus;
import com.bank.model.entity.Role;
import com.bank.model.exception.client.CreateClientException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class RegisterServiceImpl implements RegisterService {
    private static final Logger LOG = LogManager.getLogger(RegisterServiceImpl.class);
    private final ClientDao clientDao;
    public RegisterServiceImpl(ClientDao clientDao) {
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

}
