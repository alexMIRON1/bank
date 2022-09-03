package command;
import com.bank.controller.command.exception.ClientBannedException;
import com.bank.controller.command.exception.WrongPasswordException;
import com.bank.controller.command.impl.LoginCommand;
import com.bank.controller.service.AuthorizedService;
import com.bank.model.entity.Client;
import com.bank.model.entity.Role;
import com.bank.model.exception.client.ReadClientException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class LoginCommandTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private AuthorizedService authorizedService;
    private static final Map<String, String[]> params = new HashMap<>();
    @Mock
    private HttpSession session;
    @BeforeClass
    public static void globalSetUp(){
        params.put("login", new String[]{"alex"});
        params.put("password",new String[]{"alex"});
    }
    @Before
    public void setUp() throws ReadClientException, ClientBannedException, WrongPasswordException {
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(authorizedService.get(params.get("login")[0],params.get("password")[0])).thenReturn(new Client());
        Mockito.when(request.getParameterMap()).thenReturn(params);
    }
    @Test
    public void testLoginExecute(){
        LoginCommand loginCommand = new LoginCommand(authorizedService);
        String path = loginCommand.execute(request);
        assertEquals("redirect:/bank/home",path);
    }
}
