package com.bank.controller.service;

import com.bank.model.entity.Client;
import com.bank.model.exception.client.CreateClientException;

import java.util.List;

public interface RegisterService {
    void create(Client client ,String login, String password, String passwordConfirm) throws CreateClientException;;
}
