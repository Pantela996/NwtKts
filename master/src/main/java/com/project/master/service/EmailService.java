package com.project.master.service;


import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

import static com.project.master.service.QRCodeGenerator.generateQRCodeImage;

@Service
public class EmailService {
    private JavaMailSender javaMailSender;

    private static final String QR_CODE_IMAGE_PATH = "./static/tickets/MyQRCode.png";

    @Autowired
    public EmailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail() throws MailException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(msg, true);
            helper.setTo("mihajlo.jovkovic@gmail.com");
            helper.setFrom("mihajlomiskjovkovic@gmail.com");

            helper.setSubject("Testing from Spring Boot");
            helper.setText("<h1>Check attachment for image!</h1>", true);

            generateQRCodeImage("Volim guzu", 350, 350, QR_CODE_IMAGE_PATH);

            FileSystemResource file = new FileSystemResource(new File("static/tickets/MyQRCode.png"));
            helper.addAttachment("Ticket.png", file);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }

        javaMailSender.send(msg);


    }

}
