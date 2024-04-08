package vn.edu.iuh.fit.process.read.invoice;

import com.google.gson.Gson;
import vn.edu.iuh.fit.core.Invoice;
import vn.edu.iuh.fit.core.InvoiceInfo;
import vn.edu.iuh.fit.core.entities.IFilter;
import vn.edu.iuh.fit.core.entities.IMessage;

import java.util.ArrayList;
import java.util.List;

public class InvoiceReader implements IFilter<IMessage> {
    private final Gson GSON = new Gson();

    @Override
    public IMessage execute(IMessage message) {
        // 1. Read the invoice data from file content comes inside the message
        //parse to structured data
        String fileContent = message.getFileContent();
        InvoiceInfo invoiceInfo = GSON.fromJson(fileContent, InvoiceInfo.class);
        List<Invoice> invoices = invoiceInfo.getInvoices();

        // 2. Set the invoice data in invoiceInfo object comes with the message
        message.getInvoiceInfo().setInvoices(invoices);

        // 3. Return the update message objected back to caller
        return message;
    }
}
//vovanhai-ueh