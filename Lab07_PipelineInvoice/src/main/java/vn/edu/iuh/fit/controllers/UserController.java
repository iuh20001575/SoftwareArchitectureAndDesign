package vn.edu.iuh.fit.controllers;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpSession;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.edu.iuh.fit.configs.ApplicationContextProvider;
import vn.edu.iuh.fit.core.*;
import vn.edu.iuh.fit.models.Product;
import vn.edu.iuh.fit.repositories.ProductRepository;

import java.util.*;

@Controller
@RequestMapping("/")
public class UserController {

    private final ProductRepository productRepository;
    private final JmsTemplate jmsTemplate = ApplicationContextProvider.getApplicationContext().getBean(JmsTemplate.class);
    private final Gson GSON = new Gson();

    public UserController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Product> products = productRepository.findAll();

        model.addAttribute("products", products);

        return "user/index";
    }@GetMapping("/checkout")
    public String checkout(Model model, HttpSession session) {
        Object cart = session.getAttribute("cart");
        Map<Long, Float> map;

        if (cart == null)
            map = new HashMap<>();
        else map = (Map<Long, Float>) cart;

        List<Product> products = productRepository.findByIds(map.keySet().stream().toList());
        double total = 0;
        int length = products.size();
        List<Float> quantities = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            Product product = products.get(i);

            float quantity = map.get(product.getId());

            total += quantity * product.getPrice();
            quantities.add(map.get(product.getId()));
        }

        Payment payment = new Payment();
        Note no = new Note();
        CreditNote creditNote = new CreditNote();

        model.addAttribute("cartSize", cart == null ? 0 : map.size());
        model.addAttribute("products", products);
        model.addAttribute("quantities", quantities);
        model.addAttribute("total", total);
        model.addAttribute("payment", payment);
        model.addAttribute("no", no);
        model.addAttribute("creditNote", creditNote);

        return "user/checkout";
    }

    @PostMapping("/checkout")
    public String placeOrder(HttpSession session, @ModelAttribute Payment payment, @ModelAttribute Note no, @ModelAttribute CreditNote creditNote) {
        Map<Long, Float> map = (Map<Long, Float>) session.getAttribute("cart");
        System.out.println("map: " + map);

        long custId = new Random().nextLong();

        List<Invoice> invoices = new ArrayList<>();

        map.forEach((aLong, aFloat) -> {
            Invoice invoice = new Invoice(custId, aLong, aFloat, "");

            invoices.add(invoice);
        });

        payment.setCustId(custId);
        no.setDelivery(false);
        creditNote.setCancellation(false);

        InvoiceInfo invoiceInfo = new InvoiceInfo();
        invoiceInfo.setInvoices(invoices);
        invoiceInfo.setNotes(List.of(no));
        invoiceInfo.setPayments(List.of(payment));
        invoiceInfo.setCreditNotes(List.of(creditNote));

        jmsTemplate.convertAndSend("order", GSON.toJson(invoiceInfo));

        session.setAttribute("cart", null);

        return "redirect:/";
    }
}
