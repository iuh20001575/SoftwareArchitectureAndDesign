package vn.edu.iuh.fit.services;

import com.thedeanda.lorem.LoremIpsum;
import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import jakarta.mail.MessagingException;
import org.apache.activemq.Message;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.core.*;
import vn.edu.iuh.fit.core.entities.IFilter;
import vn.edu.iuh.fit.core.entities.IMessage;
import vn.edu.iuh.fit.models.Product;
import vn.edu.iuh.fit.pipes.Pipeline;
import vn.edu.iuh.fit.pipes.PipelineBase;
import vn.edu.iuh.fit.process.read.credit.CreditNoteReader;
import vn.edu.iuh.fit.process.read.invoice.InvoiceReader;
import vn.edu.iuh.fit.process.read.note.NoteReader;
import vn.edu.iuh.fit.process.read.payment.PaymentReader;
import vn.edu.iuh.fit.repositories.*;
import vn.edu.iuh.fit.utils.Mail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class OrderListenerImpl {
    private final ProductRepository productRepository;
    private final CreditNoteRepository creditNoteRepository;
    private final InvoiceRepository invoiceRepository;
    private final NoteRepository noteRepository;
    private final PaymentRepository paymentRepository;
    private final MailServicesImpl mailServices;

    public OrderListenerImpl(ProductRepository productRepository,
                             CreditNoteRepository creditNoteRepository,
                             InvoiceRepository invoiceRepository,
                             NoteRepository noteRepository,
                             PaymentRepository paymentRepository,
                             MailServicesImpl mailServices) {
        this.productRepository = productRepository;
        this.creditNoteRepository = creditNoteRepository;
        this.invoiceRepository = invoiceRepository;
        this.noteRepository = noteRepository;
        this.paymentRepository = paymentRepository;
        this.mailServices = mailServices;
    }

    @JmsListener(destination = "order")
    public void receiveMessage(Message message) throws JMSException, MessagingException {
        if (message instanceof TextMessage) {
            String messData = ((TextMessage) message).getText();

            List<IFilter<IMessage>> filters = new ArrayList<>();
            vn.edu.iuh.fit.core.entities.IMessage messagePipeline = new vn.edu.iuh.fit.core.Message();
            messagePipeline.setFileContent(messData);
            // 1. Create the filter list first
            filters.add(new InvoiceReader());
            filters.add(new PaymentReader());
            filters.add(new NoteReader());
            filters.add(new CreditNoteReader());

            // 2. Create Pipe with list of filter
            PipelineBase<IMessage> pipeLine = new Pipeline(filters);

            // 3. Perform execution
            IMessage result = pipeLine.ProcessFilters(messagePipeline);

            InvoiceInfo invoiceInfo = result.getInvoiceInfo();
            List<Invoice> invoices = invoiceInfo.getInvoices();
            HashMap<Long, Float> map = new HashMap<>();

            invoices.forEach(invoice -> map.put(invoice.getProductId(), invoice.getQuantity()));
            List<Long> productIds = map.keySet().stream().toList();

            AtomicBoolean isEnough = new AtomicBoolean(true);
            List<Product> products = productRepository.findByIds(productIds);
            List<String> productNames = new ArrayList<>();
            List<String> unavailableProductNames = new ArrayList<>();

            products.forEach(product -> {
                int quantity = product.getQuantity();
                Float quantityNeedToBuy = map.get(product.getId());

                productNames.add(product.getName());

                if (quantity >= quantityNeedToBuy)
                    product.setQuantity((int) (quantity - quantityNeedToBuy));
                else {
                    isEnough.set(false);
                    unavailableProductNames.add(product.getName());
                }
            });

            //4. make order or reject
            String subject, content;
            LoremIpsum instance = LoremIpsum.getInstance();
            List<Note> notes = invoiceInfo.getNotes();
            List<Payment> payments = invoiceInfo.getPayments();
            List<CreditNote> creditNotes = invoiceInfo.getCreditNotes();

            boolean isOrderSuccess = isEnough.get();

            if (isOrderSuccess) {
                productRepository.saveAll(products);

                subject = "Confirmation of Successful Order Placement";
                content = Mail.getSuccessContent(instance.getName(), new Random().nextInt(), LocalDate.now(), productNames);
            } else {
                subject = "Notification of Unsuccessful Order Placement";
                content = Mail.getFailContent(instance.getName(), new Random().nextInt(), LocalDate.now(), productNames, unavailableProductNames);
            }

            creditNotes.forEach(c -> c.setCancellation(isOrderSuccess));
            notes.forEach(n -> n.setDelivery(isOrderSuccess));

            creditNoteRepository.saveAll(creditNotes);
            invoiceRepository.saveAll(invoices);
            noteRepository.saveAll(notes);
            paymentRepository.saveAll(payments);

            //5. send email
            mailServices.send("thaoanhhaa1@gmail.com", subject, content);
        }

    }
}
