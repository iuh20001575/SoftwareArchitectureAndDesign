package vn.iuh.iuh.fit.services;

import org.apache.activemq.Message;

public interface OrderListeners {
    void receiveMessage(Message message);
}
