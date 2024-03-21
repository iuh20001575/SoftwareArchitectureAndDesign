package vn.iuh.iuh.fit.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.iuh.iuh.fit.configs.ApplicationContextProvider;
import vn.iuh.iuh.fit.dto.OrderDTO;
import vn.iuh.iuh.fit.models.Address;
import vn.iuh.iuh.fit.models.Customer;
import vn.iuh.iuh.fit.repositories.OrderRepository;
import vn.iuh.iuh.fit.models.Product;
import vn.iuh.iuh.fit.repositories.ProductRepository;
import vn.iuh.iuh.fit.utils.JsonUtil;
import vn.iuh.iuh.fit.utils.crypting.Base64EncodingText;

import java.util.*;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    private final JmsTemplate jmsTemplate = ApplicationContextProvider.getApplicationContext().getBean(JmsTemplate.class);

    @GetMapping({"", "/"})
    public String index(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, Model model, HttpSession session) {
        Integer pageNumber = page.orElse(1);
        Integer pageSize = size.orElse(9);

        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Order.asc("name")));
        Page<Product> products = productRepository.findAll(pageRequest);

        Object cart = session.getAttribute("cart");
        Map<Long, Float> map = (Map<Long, Float>) cart;

        model.addAttribute("products", products);
        model.addAttribute("pages", IntStream.range(1, products.getTotalPages() + 1).boxed().toList());
        model.addAttribute("page", products.getNumber() + 1);
        model.addAttribute("size", 9);
        model.addAttribute("cartSize", cart == null ? 0 : map.size());

        return "user/index";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, HttpSession session) {
        Address address = new Address();
        Customer customer = new Customer();
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

        model.addAttribute("cartSize", cart == null ? 0 : map.size());
        model.addAttribute("products", products);
        model.addAttribute("quantities", quantities);
        model.addAttribute("total", total);
        model.addAttribute("customer", customer);
        model.addAttribute("addr", address);

        return "user/checkout";
    }

    @PostMapping("/checkout")
    public String placeOrder(@ModelAttribute Customer customer, @ModelAttribute Address address, HttpSession session) {
        Map<Long, Float> map = (Map<Long, Float>) session.getAttribute("cart");
        Base64EncodingText encodingText = new Base64EncodingText();

        customer.setAddress(address);

        OrderDTO orderDTO = new OrderDTO(customer, map);
        String json = JsonUtil.toJson(orderDTO);
        jmsTemplate.convertAndSend("order", encodingText.encrypt(json));

        session.setAttribute("cart", null);

        return "redirect:/";
    }
}
