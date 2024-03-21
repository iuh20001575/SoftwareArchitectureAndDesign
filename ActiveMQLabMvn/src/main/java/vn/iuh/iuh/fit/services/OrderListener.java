package vn.iuh.iuh.fit.services;

import jakarta.jms.JMSException;
import jakarta.mail.MessagingException;
import org.apache.activemq.Message;

public interface OrderListener {
    void receiveMessage(final Message message) throws JMSException, MessagingException;
}
