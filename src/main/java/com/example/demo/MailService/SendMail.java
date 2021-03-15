package com.example.demo.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class SendMail {
    @Autowired
    private JavaMailSender javaMailSender;


    public SendMail(JavaMailSender javaMailSender){
        this.javaMailSender= javaMailSender;

    }


    public void sendMail(String email,String message) throws MailException {
        Thread t= new Thread(()-> {

            MimeMessage messagetoclient = javaMailSender.createMimeMessage();


            try {
                MimeMessageHelper helper = new MimeMessageHelper(messagetoclient,true);
                helper.setTo(email);
                helper.setFrom("Kea.online@outlook.com");
                helper.setSubject("From KEA:");
                helper.setText(message);
                javaMailSender.send(messagetoclient);

            } catch (MessagingException e) {
                e.printStackTrace();
            }



        });
        t.start();
    }

}