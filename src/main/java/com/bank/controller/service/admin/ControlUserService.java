package com.bank.controller.service.admin;

import com.bank.model.entity.Client;
import com.bank.model.exception.client.ReadClientException;
import com.bank.model.exception.client.UpdateClientException;

public interface ControlUserService {
    /**
     * get client
     * @param id client's id
     * @return client
     * @throws ReadClientException when client does not exist
     */
    Client read(Integer id) throws ReadClientException;

    /**
     * update client
     * @param client for updating
     * @throws UpdateClientException when wrong data for updating
     */
    void update(Client client) throws UpdateClientException;
}
