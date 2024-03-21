package vn.iuh.iuh.fit.services.impl;

import com.google.gson.Gson;
import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import jakarta.mail.MessagingException;
import org.apache.activemq.Message;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import vn.iuh.iuh.fit.dto.OrderDTO;
import vn.iuh.iuh.fit.models.*;
import vn.iuh.iuh.fit.repositories.*;
import vn.iuh.iuh.fit.services.MailServices;
import vn.iuh.iuh.fit.services.OrderListener;
import vn.iuh.iuh.fit.utils.crypting.Base64EncodingText;
import vn.iuh.iuh.fit.utils.crypting.EncodingText;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class OrderListenerImpl implements OrderListener {
    private final ProductRepository productRepository;
    private final ProductQuantityRepository productQuantityRepository;
    private final MailServices mailServices;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderListenerImpl(ProductRepository productRepository,
                             ProductQuantityRepository productQuantityRepository, MailServices mailServices,
                             CustomerRepository customerRepository,
                             OrderRepository orderRepository,
                             OrderDetailRepository orderDetailRepository) {
        this.productRepository = productRepository;
        this.productQuantityRepository = productQuantityRepository;
        this.mailServices = mailServices;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    @JmsListener(destination = "order")
    public void receiveMessage(Message message) throws JMSException, MessagingException {
        if (message instanceof TextMessage) {
            //1. read message data
            String messData = ((TextMessage) message).getText();

            //2. ==> decode
            EncodingText encodingText = new Base64EncodingText();
            String mess = encodingText.decrypt(messData);

            Gson gson = new Gson();
            OrderDTO orderDTO = gson.fromJson(mess, OrderDTO.class);
            Customer customer = orderDTO.getCustomer();
            Map<Long, Float> cart = orderDTO.getCart();

            //3. check for quantity
            AtomicBoolean isEnough = new AtomicBoolean(true);
            List<Product> products = productRepository.findByIds(cart.keySet().stream().toList());
            List<ProductQuantity> productQuantities = new ArrayList<>();
            List<String> productNames = new ArrayList<>();
            List<String> unavailableProductNames = new ArrayList<>();

            products.forEach(product -> {
                ProductQuantity productQuantity = product.getQuantity();
                float quantity = productQuantity.getQuantity();
                Float quantityNeedToBuy = cart.get(product.getId());
                productNames.add(product.getName());

                if (quantity >= quantityNeedToBuy){
                    productQuantity.setQuantity(quantity - quantityNeedToBuy);

                    productQuantities.add(productQuantity);
                }
                else {
                    isEnough.set(false);
                    unavailableProductNames.add(product.getName());
                }
            });

            //4. make order or reject
            String subject, content;

            if (isEnough.get()) {
                if (customerRepository.findByEmail(customer.getEmail()).isEmpty())
                    customerRepository.save(customer);

                Optional<Customer> oCustomer = customerRepository.findByEmail(customer.getEmail());
                Order order = new Order(oCustomer.get());
                List<OrderDetail> orderDetails = new ArrayList<>();

                products.forEach(product -> {
                    orderDetails.add(new OrderDetail(order, product, cart.get(product.getId())));
                });

                order.setOrderDetails(orderDetails);

                orderRepository.save(order);
                orderDetailRepository.saveAll(orderDetails);
                productQuantityRepository.saveAll(productQuantities);

                subject = "Confirmation of Successful Order Placement";
                content = String.format("Dear %s,\n", customer.getFirstName() + " " + customer.getLastName()) +
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
                        "\n" +
                        "Fruitables";
            } else {
                subject = "Notification of Unsuccessful Order Placement";
                content = String.format("Dear %s,\n", customer.getFirstName() + " " + customer.getLastName()) +
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

            //5. send email
            mailServices.send(customer.getEmail(), subject, content);
        }

    }
}
