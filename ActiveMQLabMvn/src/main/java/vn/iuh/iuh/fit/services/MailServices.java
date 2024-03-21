package vn.iuh.iuh.fit.services;

import jakarta.mail.MessagingException;

public interface MailServices {
    void send(String receiver, String subject, String content) throws MessagingException;
}
