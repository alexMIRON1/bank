package com.bank.controller.listener;

import com.bank.controller.command.Command;
import com.bank.controller.command.CommandContainer;
import com.bank.controller.command.impl.*;
import com.bank.controller.command.impl.admin.*;
import com.bank.controller.command.impl.client.*;
import com.bank.controller.service.*;
import com.bank.controller.service.admin.AdminPageService;
import com.bank.controller.service.admin.ControlUserService;
import com.bank.controller.service.admin.UnblockService;
import com.bank.controller.service.admin.impl.AdminPageServiceImpl;
import com.bank.controller.service.admin.impl.ControlUserServiceImpl;
import com.bank.controller.service.admin.impl.UnblockServiceImpl;
import com.bank.controller.service.client.*;
import com.bank.controller.service.client.impl.*;
import com.bank.controller.service.impl.*;
import com.bank.model.connection.ConnectionPool;
import com.bank.model.dao.BillDao;
import com.bank.model.dao.CardDao;
import com.bank.model.dao.ClientDao;
import com.bank.model.dao.impl.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class ContextListener implements ServletContextListener, HttpSessionListener {
    private static final Logger LOG = LogManager.getLogger(ContextListener.class);
    private static final FactoryDao factoryDao = FactoryDao.getInstance();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOG.info("Start context initialization");
        initializeServices();
        LOG.info("Services initialized");
    }
    private void initializeServices(){
        ClientDao clientDao = (ClientDao) factoryDao.getDao(DaoEnum.CLIENT_DAO);
        CardDao cardDao = (CardDao) factoryDao.getDao(DaoEnum.CARD_DAO);
        BillDao billDao = (BillDao) factoryDao.getDao(DaoEnum.BILL_DAO);
        clientDao.setDs(ConnectionPool.getDs());
        billDao.setDs(ConnectionPool.getDs());
        cardDao.setDs(ConnectionPool.getDs());
        CommandContainer container = new CommandContainer();
        //create services
        //loginPage
        container.put("/login", new LoginPageCommand());
        LOG.info("loginPageCommand successfully put");
        //registerPage
        container.put("/register", new RegisterPageCommand());
        LOG.info("registerPageCommand successfully put");
        //logout
        container.put("/logout", new LogoutCommand());
        LOG.info("logoutCommand successfully put");
        //errorPage
        container.put("/error", new ErrorPageCommand());
        LOG.info("errorPage successfully put");
        //register
        AuthorizedService authorizedService = new AuthorizedServiceImpl(clientDao);

        Command command = new RegisterCommand(authorizedService);
        container.put("/toRegister",command);
        LOG.info("registerCommand successfully put");
        //login
        command = new LoginCommand(authorizedService);
        container.put("/toLogin",command);
        LOG.info("loginCommand successfully put");
        //blockCardClient
        CardsService cardsService = new CardsServiceImpl(cardDao);
        command = new BlockCardCommand(cardsService);
        container.put("/block", command);
        //topUp
        command = new TopUpCommand(cardsService);
        container.put("/top-up",command);
        //setCustomName
        command = new SetCustomNameCommand(cardsService);
        container.put("/setName", command);
        LOG.info("setCustomNameCommand successfully put");
        //request to unblock
        command = new RequestToUnblockCommand(cardsService);
        container.put("/request-unblock", command);
        LOG.info(" requestToUnblockCommand successfully put");
        //homePage
        HomePageService homePageService = new HomePageServiceImpl(cardDao);
        command = new HomePageCommand(homePageService);
        container.put("/home",command);
        LOG.info("homePageCommand successfully put");
        //makePayment
        MakePaymentService makePaymentService = new MakePaymentServiceImpl(billDao);
        command = new MakePaymentCommand(makePaymentService);
        container.put("/make-payment",command);
        LOG.info("makePaymentCommand successfully put");
        //payBill
        BillsService billsService = new BillsServiceImpl(billDao,cardDao);
        command = new PayBillCommand(billsService);
        container.put("/pay-bill", command);
        LOG.info("payBillCommand successfully put");
        //deleteBill
        command = new DeleteBillCommand(billsService);
        container.put("/delete",command);
        LOG.info("deleteBillCommand successfully put");
        //paymentsPage
        PaymentsPageService paymentsPageService = new PaymentsPageServiceImpl(cardDao,billDao);
        command = new PaymentsPageCommand(paymentsPageService);
        container.put("/payments",command);
        LOG.info("paymentsPageCommand successfully put");
        //receiveCard
        ReceiveCardService receiveCardService = new ReceiveCardServiceImpl(cardDao);
        command = new ReceiveCardCommand(receiveCardService);
        container.put("/receive-card", command);
        LOG.info("receiveCardCommand successfully put");
        LOG.info("topUpCommand successfully put");
        //adminPage
        AdminPageService adminPageService = new AdminPageServiceImpl(cardDao,clientDao);
        command = new AdminPageCommand(adminPageService);
        container.put("/admin",command);
        LOG.info("adminPageCommand successfully put");
        //lock
        ControlUserService controlUserService = new ControlUserServiceImpl(clientDao);
        command = new LockCommand(controlUserService);
        container.put("/lock", command);
        LOG.info("lockCommand successfully put");
        //unblock
        UnblockService unblockService = new UnblockServiceImpl(cardDao);
        command = new UnblockCommand(unblockService);
        container.put("/unblock",command);
        LOG.info("unblockCommand successfully put");
        //unlock
        command = new UnlockCommand(controlUserService);
        container.put("/unlock", command);
        LOG.info("unlockCommand successfully put");
        //blockedCardPage
        container.put("/blockedCards", new BlockedCardsPageCommand(adminPageService));
        LOG.info("blockedCardsPage successfully put ");
        //aboutPage
        container.put("/about", new AboutPageCommand());
        LOG.info("aboutPage successfully put");
        //getReceipt
        container.put("/receipt",new GetReceiptCommand(billsService));

    }
}
