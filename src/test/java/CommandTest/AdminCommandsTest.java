package CommandTest;

import com.bank.controller.command.impl.admin.AdminPageCommand;
import com.bank.controller.command.impl.admin.LockCommand;
import com.bank.controller.command.impl.admin.UnblockCommand;
import com.bank.controller.command.impl.admin.UnlockCommand;
import com.bank.controller.service.admin.AdminPageService;
import com.bank.controller.service.admin.ControlUserService;
import com.bank.controller.service.admin.UnblockService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class AdminCommandsTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private AdminPageService adminPageService;
    @Mock
    private ControlUserService controlUserService;
    @Mock
    private UnblockService unblockService;
    @Before
    public void setUp(){
        Mockito.when(request.getParameter(Mockito.anyString())).thenReturn("1");
    }
    @Test
    public void testAdminPageExecute(){
        AdminPageCommand adminPageCommand = new AdminPageCommand(adminPageService);
        String path = adminPageCommand.execute(request);
        assertEquals("/admin.jsp",path);
    }
    @Test
    public void testLockExecute(){
        LockCommand lockCommand = new LockCommand(controlUserService);
        String path = lockCommand.execute(request);
        assertEquals("redirect:/bank/admin",path);

    }
    @Test
    public void UnBlockExecute(){
        UnblockCommand unblockCommand = new UnblockCommand(unblockService);
        String path = unblockCommand.execute(request);
        assertEquals("redirect:/bank/admin",path);
    }
    @Test
    public void UnLockExecute(){
        UnlockCommand unlockCommand = new UnlockCommand(controlUserService);
        String path = unlockCommand.execute(request);
        assertEquals("redirect:/bank/admin",path);
    }
}
