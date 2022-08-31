package com.bank.controller.command.impl.client;
import com.bank.controller.command.Command;
import com.bank.controller.command.exception.WrongDataEmailException;
import com.bank.controller.service.client.BillsService;
import com.bank.model.entity.Bill;
import com.bank.model.entity.Card;
import com.bank.model.exception.bill.ReadBillException;
import com.bank.model.exception.card.ReadCardException;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;

public class GetReceiptCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(GetReceiptCommand.class);
    private final BillsService billsService;
    private static final String FROM = "andrej14883642@gmail.com";

    public GetReceiptCommand(BillsService billsService) {
        this.billsService = billsService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Integer billId = Integer.parseInt(request.getParameter("bill"));
        String email = request.getParameter("email");
        try {
            Bill bill = billsService.read(billId);
            Card card = billsService.fillCard(bill.getCard().getId());
            makeReceipt(card, bill);
            LOG.debug("make receipt was successfully");
            sendEmail(email);
            LOG.debug("email was successfully send");
        } catch (ReadCardException | ReadBillException e) {
            LOG.debug("fail to obtain bill-->such bill does not exist",e);
            return "/error.jsp";
        } catch (DocumentException | IOException e) {
            LOG.debug("Something wrong with data, password, email and etc.", e);
            return "/error.jsp";
        }
        return "/payments.jsp";
    }

    private void makeReceipt(Card card, Bill bill) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("receipt.pdf"));
        document.open();
        LOG.debug("document was open");
        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_BOLD, 16, BaseColor.BLACK);
        Font fontUsual = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, BaseColor.BLACK);
        Paragraph cardP = new Paragraph("Card", fontTitle);
        cardP.setAlignment(Element.ALIGN_CENTER);
        document.add(cardP);
        document.add(new Chunk("Card id: " + card.getName(),fontUsual));
        document.add(new Chunk("\nCard name: " + card.getCustomName(),fontUsual));
        Paragraph billP = new Paragraph("\nBill", fontTitle);
        cardP.setAlignment(Element.ALIGN_CENTER);
        document.add(billP);
        document.add(new Chunk("Bill id: " + bill.getId(),fontUsual));
        document.add(new Chunk("\nBill sum:" + bill.getSum(), fontUsual));
        document.add(new Chunk("\nBill date: "+ bill.getDate(),fontUsual));
        document.add(new Paragraph("\nRecipient id card: " + bill.getRecipient(),fontTitle));
        Paragraph end = new Paragraph("Thanks for using IBANK!", fontTitle);
        end.setAlignment(Element.ALIGN_CENTER);
        document.add(end);
        LOG.debug("successfully add text to document");
        document.close();
        writer.close();
        LOG.debug("document and writer closed");
    }

    private  void sendEmail(String to) {
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.user", FROM);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        LOG.debug("Set property");
        final String password = "zqosldapdwxrtuat";

        try {
            Session session = Session.getDefaultInstance(props,
                    new Authenticator(){
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(FROM, password);
                        }});
            LOG.debug("get session");
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(FROM));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));

            message.setSubject("Receipt");

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Your receipt");
            Multipart multipart = new MimeMultipart();

            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            String filename = "receipt.pdf";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            Transport.send(message);
            LOG.debug("message was successfully send");
        } catch (MessagingException e) {
            LOG.debug("wrong properties",e);
            throw new WrongDataEmailException("Something wrong with data, password, email and etc.");
        }
    }
}
