package command;

import com.bank.controller.command.impl.RegisterCommand;
import com.bank.controller.service.AuthorizedService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class RegisterCommandTest {
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
        params.put("password",new String[]{"1111"});
        params.put("confirm_password", new String[]{"1111"});
    }
    @Before
    public void setUp(){
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getParameterMap()).thenReturn(params);
    }
    @Test
    public void testRegisterExecute(){
        RegisterCommand registerCommand = new RegisterCommand(authorizedService);
        String path = registerCommand.execute(request);
        assertEquals("redirect:/bank/login",path);
    }
}
