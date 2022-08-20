package ServiceTest;

import com.bank.controller.service.admin.ControlUserService;
import com.bank.controller.service.admin.impl.ControlUserServiceImpl;
import com.bank.model.dao.ClientDao;
import com.bank.model.entity.Client;
import com.bank.model.entity.ClientStatus;
import com.bank.model.exception.client.ReadClientException;
import com.bank.model.exception.client.UpdateClientException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ControlUserServiceTest {
    @Mock
    private ClientDao clientDao;
    private Client client;
    private ControlUserService controlUserService;

    @Before
    public void setUp() throws ReadClientException {
        controlUserService = new ControlUserServiceImpl(clientDao);
        client = Entity.getClient(0);
        Mockito.when(clientDao.read(client.getId())).thenReturn(client);
    }
    @Test
    public void testRead() throws ReadClientException {
        Client testClient = controlUserService.read(client.getId());
        assertEquals(client.getId(),testClient.getId());
        assertEquals(client.getLogin(),testClient.getLogin());
        assertEquals(client.getClientStatus().getId(),testClient.getClientStatus().getId());
        assertEquals(client.getPassword(),testClient.getPassword());
        assertEquals(client.getRole().getId(),testClient.getRole().getId());

    }
    @Test(expected = ReadClientException.class)
    public void testWrongIdRead() throws ReadClientException {
        controlUserService.read(0);
    }
    @Test
    public void testUpdate() throws UpdateClientException {
        Client testBlockedClient = Entity.createClient();
        controlUserService.update(testBlockedClient);
        Client testUnBlockedClient  = Entity.createClient();
        testUnBlockedClient.setClientStatus(ClientStatus.BLOCKED);
        controlUserService.update(testUnBlockedClient);
        Client blockedClient = Entity.createClient();
        blockedClient.setClientStatus(ClientStatus.BLOCKED);
        Client unBlockedClient = Entity.createClient();
        unBlockedClient.setClientStatus(ClientStatus.UNBLOCKED);
        assertEquals(blockedClient.getClientStatus().getId(),testBlockedClient.getClientStatus().getId());
        assertEquals(unBlockedClient.getClientStatus().getId(),testUnBlockedClient.getClientStatus().getId());
    }
    @Test(expected = UpdateClientException.class)
    public void testWrongUpdate() throws UpdateClientException {
        Client testClient = Entity.createClient();
        testClient.setId(0);
        controlUserService.update(testClient);
    }
}
