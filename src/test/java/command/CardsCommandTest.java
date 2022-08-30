package command;

import com.bank.controller.command.impl.client.*;
import com.bank.controller.service.client.CardsService;
import com.bank.controller.service.client.ReceiveCardService;
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
public class CardsCommandTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private CardsService cardsService;
    @Mock
    private ReceiveCardService receiveCardService;
    @Mock
    private HttpSession session;
    @Before
    public void setUp(){
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getParameter(Mockito.anyString())).thenReturn("1");
        Mockito.when(session.getAttribute("page")).thenReturn(new Page());
        Mockito.when(session.getAttribute("noOfPages")).thenReturn(1);
    }
    @Test
    public void testBlockCardExecute(){
        BlockCardCommand blockCardCommand = new BlockCardCommand(cardsService);
        String path = blockCardCommand.execute(request);
        assertEquals("redirect:/bank/home?page=" + new Page().getNumber(),path);
    }
    @Test
    public void testUnBlockCardExecute(){
        RequestToUnblockCommand requestToUnblockCommand = new RequestToUnblockCommand(cardsService);
        String path  = requestToUnblockCommand.execute(request);
        assertEquals("redirect:/bank/home?page=" + new Page().getNumber(),path);
    }
    @Test
    public void testSetCustomNameExecute(){
        SetCustomNameCommand setCustomNameCommand = new SetCustomNameCommand(cardsService);
        String path  = setCustomNameCommand.execute(request);
        assertEquals("redirect:/bank/home?page=" + new Page().getNumber() +"&sort=" + new Page().getState(),path);
    }
    @Test
    public void testTopUpExecute(){
        TopUpCommand topUpCommand = new TopUpCommand(cardsService);
        String path = topUpCommand.execute(request);
        assertEquals("redirect:/bank/home?page=" + new Page().getNumber(),path);
    }
    @Test
    public void testReceiveCardExecute(){
        ReceiveCardCommand receiveCardCommand = new ReceiveCardCommand(receiveCardService);
        String path = receiveCardCommand.execute(request);
        assertEquals("redirect:/bank/home?page=1",path);
    }
}
