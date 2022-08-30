package command;

import com.bank.controller.command.impl.client.HomePageCommand;
import com.bank.controller.service.client.HomePageService;
import com.bank.model.entity.Client;
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
public class HomePageCommandTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HomePageService homePageService;
    @Mock
    private HttpSession session;
    @Before
    public void setUp(){
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute(Mockito.anyString())).thenReturn(new Client(1));
    }
    @Test
    public void testHomePageExecute(){
        HomePageCommand homePageCommand = new HomePageCommand(homePageService);
        String path = homePageCommand.execute(request);
        assertEquals("/home.jsp",path);

    }
}
