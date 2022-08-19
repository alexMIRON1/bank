package com.bank.controller.service.admin.impl;

import com.bank.controller.service.admin.LockService;
import com.bank.model.dao.ClientDao;
import com.bank.model.entity.Client;
import com.bank.model.entity.ClientStatus;
import com.bank.model.exception.client.ReadClientException;
import com.bank.model.exception.client.UpdateClientException;

public class LockServiceImpl implements LockService {
    private final ClientDao clientDao;

    public LockServiceImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public Client read(Integer id) throws ReadClientException {
        return clientDao.read(id);
    }

    @Override
    public void update(Client client) throws UpdateClientException {
        client.setClientStatus(ClientStatus.BLOCKED);
        clientDao.update(client);
    }
}
