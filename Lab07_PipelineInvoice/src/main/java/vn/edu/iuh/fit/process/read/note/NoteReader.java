package vn.edu.iuh.fit.process.read.note;

import com.google.gson.Gson;
import vn.edu.iuh.fit.core.InvoiceInfo;
import vn.edu.iuh.fit.core.Note;
import vn.edu.iuh.fit.core.entities.IFilter;
import vn.edu.iuh.fit.core.entities.IMessage;

import java.util.ArrayList;
import java.util.List;

public class NoteReader implements IFilter<IMessage> {
    @Override
    public IMessage execute(IMessage message) {
        //1. Read the note content from the file content comes with the message
        InvoiceInfo invoiceInfo = new Gson().fromJson(message.getFileContent(), InvoiceInfo.class);
        List<Note> notes = invoiceInfo.getNotes();

        // 2. Update the message with the notes read
        message.getInvoiceInfo().setNotes(notes);

        // 3. Return the updated message back to the caller


        return message;
    }
}
