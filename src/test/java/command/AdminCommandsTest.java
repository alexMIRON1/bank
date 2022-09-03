package command;

import com.bank.controller.command.impl.admin.AdminPageCommand;
import com.bank.controller.command.impl.admin.LockCommand;
import com.bank.controller.command.impl.admin.UnblockCommand;
import com.bank.controller.command.impl.admin.UnlockCommand;
import com.bank.controller.service.admin.AdminPageService;
import com.bank.controller.service.admin.ControlUserService;
import com.bank.controller.service.admin.UnblockService;
import com.bank.model.entity.Page;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    @Mock
    private HttpSession session;
    @Before
    public void setUp(){
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getParameter(Mockito.anyString())).thenReturn("1");
        Mockito.when(session.getAttribute(Mockito.anyString())).thenReturn(new Page());
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
        assertEquals("redirect:/bank/admin?page=" + new Page().getNumber(),path);

    }
    @Test
    public void UnBlockExecute(){
        UnblockCommand unblockCommand = new UnblockCommand(unblockService);
        String path = unblockCommand.execute(request);
        assertEquals("redirect:/bank/blockedCards?page=" + new Page().getNumber(),path);
    }
    @Test
    public void UnLockExecute(){
        UnlockCommand unlockCommand = new UnlockCommand(controlUserService);
        String path = unlockCommand.execute(request);
        assertEquals("redirect:/bank/admin?page=" + new Page().getNumber(),path);
    }
}
