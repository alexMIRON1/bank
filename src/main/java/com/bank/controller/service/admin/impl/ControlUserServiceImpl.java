package com.bank.controller.service.admin.impl;

import com.bank.controller.service.admin.ControlUserService;
import com.bank.model.dao.ClientDao;
import com.bank.model.entity.Client;
import com.bank.model.entity.ClientStatus;
import com.bank.model.exception.client.ReadClientException;
import com.bank.model.exception.client.UpdateClientException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ControlUserServiceImpl implements ControlUserService {
    private final ClientDao clientDao;
    private static final Logger LOG = LogManager.getLogger(ControlUserServiceImpl.class);

    public ControlUserServiceImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public Client read(Integer id) throws ReadClientException {
        if(id == 0){
            LOG.debug("client with id" + id + "does not exist");
            throw  new ReadClientException();
        }
        return clientDao.read(id);
    }

    @Override
    public void update(Client client) throws UpdateClientException {
        if(client.getId() == 0){
            LOG.debug("client with id" + client.getId() + "does not exist");
            throw new UpdateClientException();
        }
        if(client.getClientStatus().getId().equals(ClientStatus.UNBLOCKED.getId())){
            client.setClientStatus(ClientStatus.BLOCKED);
        }else{
            client.setClientStatus(ClientStatus.UNBLOCKED);
        }
        clientDao.update(client);
    }
}
