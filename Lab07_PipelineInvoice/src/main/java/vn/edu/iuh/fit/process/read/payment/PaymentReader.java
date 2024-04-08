package vn.edu.iuh.fit.process.read.payment;

import com.google.gson.Gson;
import vn.edu.iuh.fit.core.InvoiceInfo;
import vn.edu.iuh.fit.core.Payment;
import vn.edu.iuh.fit.core.entities.IFilter;
import vn.edu.iuh.fit.core.entities.IMessage;

import java.util.ArrayList;
import java.util.List;

public class PaymentReader implements IFilter<IMessage> {
    @Override
    public IMessage execute(IMessage message) {
        // 1. Read the payments from file Content
        InvoiceInfo invoiceInfo = new Gson().fromJson(message.getFileContent(), InvoiceInfo.class);
        List<Payment> payments = invoiceInfo.getPayments();

        // 2. Set the payments in Invoice Info object comes with the message
        message.getInvoiceInfo().setPayments(payments);

        // 3. Return the updated message object back to caller
        return message;
    }
}
