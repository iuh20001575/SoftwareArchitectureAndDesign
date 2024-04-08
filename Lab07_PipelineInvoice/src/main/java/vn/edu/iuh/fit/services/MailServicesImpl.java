package vn.edu.iuh.fit.services;

import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class MailServicesImpl {
    public void send(String receiver, String subject, String content) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", 25);

        // get Session
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("haubach159@gmail.com", "sstm hjrf zwur jrbi");
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setRecipients(Message.RecipientType.TO, receiver);
        message.setSubject(subject);
        message.setText(content);

        Transport.send(message);
    }
}
