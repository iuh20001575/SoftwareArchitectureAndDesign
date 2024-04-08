package vn.edu.iuh.fit.utils;

import java.time.LocalDate;
import java.util.List;

public class Mail {
    public static String getSuccessContent(String custName, int orderNumber, LocalDate orderDate, List<String> productNames) {
        return String.format("Dear %s,\n", custName) +
                "\n" +
                "I hope this email finds you well.\n" +
                "\n" +
                "I am writing to confirm that we have received your order and it has been successfully placed. " +
                "We are delighted to inform you that your items are now being processed and will soon be " +
                "on their way to you.\n" +
                "\n" +
                "Here are the details of your order:\n" +
                "\n" +
                String.format("Order Number: %s\n", orderNumber) +
                String.format("Date of Order: %s\n", orderDate) +
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
    }

    public static String getFailContent(String custName, int orderNumber, LocalDate orderDate, List<String> productNames, List<String> unavailableProductNames) {
        return String.format("Dear %s,\n", custName) +
                "\n" +
                "We hope this email finds you well.\n" +
                "\n" +
                "We regret to inform you that we were unable to fulfill your recent order due to " +
                "insufficient stock of certain items. We understand the inconvenience this may " +
                "cause and sincerely apologize for any disappointment.\n" +
                "\n" +
                "Here are the details of your unsuccessful order:\n" +
                "\n" +
                String.format("Order Number: %s\n", orderNumber) +
                String.format("Date of Order: %s\n", orderDate) +
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
}
