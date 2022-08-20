package com.bank.controller.service.admin;

import com.bank.model.entity.Client;
import com.bank.model.exception.client.ReadClientException;
import com.bank.model.exception.client.UpdateClientException;

public interface ControlUserService {
    Client read(Integer id) throws ReadClientException;
    void update(Client client) throws UpdateClientException;
}
