package service;

import com.bank.controller.command.exception.ClientBannedException;
import com.bank.controller.command.exception.WrongPasswordException;
import com.bank.controller.service.AuthorizedService;
import com.bank.controller.service.impl.AuthorizedServiceImpl;
import com.bank.model.dao.ClientDao;
import com.bank.model.entity.Client;
import com.bank.model.entity.ClientStatus;
import com.bank.model.exception.client.CreateClientException;
import com.bank.model.exception.client.ReadClientException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthorizedServiceTest {
    @Mock
    ClientDao clientDao;
    private Client client;
    @Before
    public void setUp() throws ReadClientException {
        client = Entity.getClient(0);
        Mockito.when(clientDao.getClient(client.getLogin())).thenReturn(client);
    }

    @Test
    public void testGet() throws ReadClientException, ClientBannedException, WrongPasswordException {
        AuthorizedService loginService = new AuthorizedServiceImpl(clientDao);
        Client testClient = loginService.get(client.getLogin(), client.getPassword());
        assertEquals(client.getLogin(),testClient.getLogin());
        assertEquals(client.getId(),testClient.getId());
        assertEquals(client.getClientStatus().getId(),testClient.getClientStatus().getId());
        assertEquals(client.getRole().getId(),client.getRole().getId());
    }
    @Test(expected = NullPointerException.class)
    public void testWrongIdClient() throws ReadClientException, ClientBannedException, WrongPasswordException {
        AuthorizedService authorizedService = new AuthorizedServiceImpl(clientDao);
        authorizedService.get("aaa","aaa");
    }
    @Test(expected = ClientBannedException.class)
    public void testClientBanned() throws ReadClientException, ClientBannedException, WrongPasswordException {
        AuthorizedService authorizedService = new AuthorizedServiceImpl(clientDao);
        client.setClientStatus(ClientStatus.BLOCKED);
        authorizedService.get(client.getLogin(), client.getPassword());
    }
    @Test(expected = WrongPasswordException.class)
    public void testWrongPassword() throws ReadClientException, ClientBannedException, WrongPasswordException {
        AuthorizedService authorizedService = new AuthorizedServiceImpl(clientDao);
        authorizedService.get(client.getLogin(),"" );
    }
    @Test
    public void testCreate() throws CreateClientException {
        AuthorizedService authorizedService = new AuthorizedServiceImpl(clientDao);
        String login = client.getLogin();
        String password = client.getPassword();
        Client testClient = new Client(1);
        client.setClientStatus(ClientStatus.UNBLOCKED);
        authorizedService.create(testClient,login,password,password);
        assertEquals(client.getId(),testClient.getId());
        assertEquals(client.getLogin(),testClient.getLogin());
        assertEquals(client.getClientStatus().getId(),testClient.getClientStatus().getId());
        assertEquals(client.getPassword(),testClient.getPassword());
        assertEquals(client.getRole().getId(),testClient.getRole().getId());
    }
    @Test(expected = CreateClientException.class)
    public void testWrongCreate() throws CreateClientException {
        AuthorizedService authorizedService = new AuthorizedServiceImpl(clientDao);
        String login = client.getLogin();
        String password = client.getPassword();
        Client testClient = new Client(1);
        authorizedService.create(testClient,login,password,"password");
    }
}
