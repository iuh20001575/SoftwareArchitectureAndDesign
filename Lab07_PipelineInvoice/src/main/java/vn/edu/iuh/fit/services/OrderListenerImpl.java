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

            //3. check for quantity
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
                content = String.format("Dear %s,\n", instance.getName()) +
                        "\n" +
                        "I hope this email finds you well.\n" +
                        "\n" +
                        "I am writing to confirm that we have received your order and it has been successfully placed. " +
                        "We are delighted to inform you that your items are now being processed and will soon be " +
                        "on their way to you.\n" +
                        "\n" +
                        "Here are the details of your order:\n" +
                        "\n" +
                        String.format("Order Number: %s\n", new Random().nextInt()) +
                        String.format("Date of Order: %s\n", LocalDate.now()) +
                        String.format("Items Ordered: %s\n", String.join(", ", productNames)) +
                        "Our team is currently working diligently to prepare your order for shipment. " +
                        "You can expect to receive a notification with tracking information once " +
                        "your package has been dispatched.\n" +
                        "\n" +
                        "We greatly appreciate your business and trust in our products/services. " +
                        "Thank you for choosing us.\n" +
                        "\n" +
                        "Best regards,\n" +
                        "\nFruitables";
            } else {
                subject = "Notification of Unsuccessful Order Placement";
                content = String.format("Dear %s,\n", instance.getName()) +
                        "\n" +
                        "We hope this email finds you well.\n" +
                        "\n" +
                        "We regret to inform you that we were unable to fulfill your recent order due to " +
                        "insufficient stock of certain items. We understand the inconvenience this may " +
                        "cause and sincerely apologize for any disappointment.\n" +
                        "\n" +
                        "Here are the details of your unsuccessful order:\n" +
                        "\n" +
                        String.format("Order Number: %s\n", new Random().nextInt()) +
                        String.format("Date of Order: %s\n", LocalDate.now()) +
                        String.format("Items Ordered: %s\n", String.join(", ", productNames)) +
                        String.format("Unavailable Items: %s\n", String.join(", ", unavailableProductNames)) +
                        "We assure you that we are actively working to restock these items and make them " +
                        "available for purchase as soon as possible. In the meantime, we would be happy " +
                        "to assist you in finding alternative products or providing updates on the " +
                        "availability of the items you were interested in.\n" +
                        "\n" +
                        "If you would like to proceed with any available items or require assistance " +
                        "with your order, please don't hesitate to reach out to our customer service " +
                        "team at [Số điện thoại hoặc địa chỉ email của dịch vụ khách hàng]. " +
                        "We are here to help and ensure your shopping experience with us is as smooth as possible.\n" +
                        "\n" +
                        "Once again, we apologize for any inconvenience caused and appreciate your " +
                        "understanding and patience in this matter.\n" +
                        "\n" +
                        "Best regards,\n" +
                        "\n" +
                        "Fruitables";
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
