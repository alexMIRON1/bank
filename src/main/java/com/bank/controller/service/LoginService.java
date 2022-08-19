package com.bank.controller.service;

import com.bank.controller.command.exception.ClientBannedException;
import com.bank.controller.command.exception.WrongPasswordException;
import com.bank.model.entity.Client;
import com.bank.model.exception.client.ReadClientException;

public interface LoginService{
    Client get(String login, String password) throws ReadClientException, WrongPasswordException, ClientBannedException;
}
