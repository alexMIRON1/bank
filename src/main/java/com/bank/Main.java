package com.bank;

import com.bank.controller.command.exception.WrongDataEmailException;
import com.bank.model.entity.Bill;
import com.bank.model.entity.Card;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Main {
    private static final String FROM = "andrej14883642@gmail.com";
    private static final String PATH_FILE = "src/main/webapp/WEB-INF/files/receipt.pdf";
    public static void main(String[] args) throws Exception {
//        System.out.println("alex1231".matches("^[a-zA-Z]{3,30}$|^[а-яА-Я]{3,30}$"))
    }
}
