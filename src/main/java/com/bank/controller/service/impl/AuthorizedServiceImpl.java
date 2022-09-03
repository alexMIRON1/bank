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
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;

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
        hashPassword(client);
        client.setClientStatus(ClientStatus.UNBLOCKED);
        client.setRole(Role.CLIENT);
        clientDao.create(client);
    }

    @Override
    public Client get(String login, String password) throws ReadClientException, WrongPasswordException, ClientBannedException {
        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512();
        byte[] passwordToEncode = password.getBytes();
        String encodedPassword = Hex.toHexString(digestSHA3.digest(passwordToEncode));
        Client client = clientDao.getClient(login);
        if(!client.getPassword().equals(encodedPassword)){
            LOG.debug("wrong password");
            throw new WrongPasswordException();
        }
        if(client.getClientStatus().equals(ClientStatus.BLOCKED)){
            LOG.debug("client " + login + "was banned");
            throw new ClientBannedException();
        }
        return client;
    }

    /**
     * hex password
     * @param client client whose password hashing
     */
    private void hashPassword(Client client){
        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512();
        byte[] passwordToEncode = client.getPassword().getBytes();
        String encodedPasswordInHex = Hex.toHexString(digestSHA3.digest(passwordToEncode));
        client.setPassword(encodedPasswordInHex);
    }
}
