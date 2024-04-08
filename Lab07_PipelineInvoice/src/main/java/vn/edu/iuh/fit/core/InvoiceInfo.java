package vn.edu.iuh.fit.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import vn.edu.iuh.fit.core.Invoice;
import vn.edu.iuh.fit.core.Note;
import vn.edu.iuh.fit.core.Payment;

import java.util.ArrayList;
import java.util.List;


@Getter
public class InvoiceInfo {
    private List<Invoice> invoices;
    private List<Payment> payments;
    private List<Note> notes;
    private List<CreditNote> creditNotes;

    public InvoiceInfo() {
        invoices = new ArrayList<>();
        payments = new ArrayList<>();
        notes = new ArrayList<>();
        creditNotes = new ArrayList<>();
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public void setCreditNotes(List<CreditNote> creditNotes) {
        this.creditNotes = creditNotes;
    }

    public String toJson() {
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return "InvoiceInfo{" +
                "invoices=" + invoices +
                ", payments=" + payments +
                ", notes=" + notes +
                ", creditNotes=" + creditNotes +
                '}';
    }
}
