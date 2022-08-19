package CommandTest;
import com.bank.controller.command.impl.LoginCommand;
import com.bank.controller.service.LoginService;
import com.bank.model.entity.Client;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class LoginCommandTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private LoginService loginService;
    private static final Map<String, String[]> params = new HashMap<>();
    @Mock
    private HttpSession session;
    @BeforeClass
    public static void globalSetUp(){
        params.put("login", new String[]{"alex"});
        params.put("password",new String[]{"alex"});
    }
    @Before
    public void setUp(){
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getParameterMap()).thenReturn(params);
        Mockito.doNothing().when(session).setAttribute("client", new Client());
    }
    @Test
    public void testLoginExecute(){
        LoginCommand loginCommand = new LoginCommand(loginService);
        String path = loginCommand.execute(request);
        assertEquals("redirect:/bank/home",path);
    }
}
