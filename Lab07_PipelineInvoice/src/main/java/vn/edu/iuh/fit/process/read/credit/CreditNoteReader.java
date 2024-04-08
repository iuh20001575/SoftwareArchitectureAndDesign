package vn.edu.iuh.fit.process.read.credit;

import com.google.gson.Gson;
import vn.edu.iuh.fit.core.CreditNote;
import vn.edu.iuh.fit.core.InvoiceInfo;
import vn.edu.iuh.fit.core.entities.IFilter;
import vn.edu.iuh.fit.core.entities.IMessage;

import java.util.ArrayList;
import java.util.List;

public class CreditNoteReader implements IFilter<IMessage> {
    @Override
    public IMessage execute(IMessage message) {
        // 1. Read the credit notes from file content
        //in this case, sample assign value instead of parsing data
        InvoiceInfo invoiceInfo = new Gson().fromJson(message.getFileContent(), InvoiceInfo.class);
        List<CreditNote> creditNotes = invoiceInfo.getCreditNotes();

        // 2. Update the message object with credit notes read
        message.getInvoiceInfo().setCreditNotes(creditNotes);

        // 3. Return the updated message back to caller
        return message;
    }
}
