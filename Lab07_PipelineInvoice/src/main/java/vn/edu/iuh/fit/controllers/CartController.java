package vn.edu.iuh.fit.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.iuh.fit.models.Product;
import vn.edu.iuh.fit.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping({"", "/"})
    public String cart(HttpSession session, Model model) {
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

        return "user/cart";
    }

    @PostMapping({"", "/"})
    public String addProductToCart(@RequestParam("product-id") Long productId, HttpSession session) {
        Map<Long, Float> map;

        Object cart = session.getAttribute("cart");

        if (cart == null)
            map = new HashMap<>();
        else map = (Map<Long, Float>) cart;

        if (map.containsKey(productId))
            map.put(productId, map.get(productId) + 1);
        else map.put(productId, 1f);

        session.setAttribute("cart", map);

        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteProductInCart(@RequestParam("product-id") Long productId, HttpSession session) {
        System.out.println(productId);

        Map<Long, Float> map = (Map<Long, Float>) session.getAttribute("cart");

        map.remove(productId);

        session.setAttribute("cart", map);

        return "redirect:/cart";
    }

    @PostMapping("/dec-product")
    public String decProduct(@RequestParam("product-id") Long productId, HttpSession session) {
        System.out.println(productId);

        Map<Long, Float> map = (Map<Long, Float>) session.getAttribute("cart");

        map.put(productId, map.get(productId) - 1);

        session.setAttribute("cart", map);

        return "redirect:/cart";
    }

    @PostMapping("/inc-product")
    public String incProduct(@RequestParam("product-id") Long productId, HttpSession session) {
        Map<Long, Float> map = (Map<Long, Float>) session.getAttribute("cart");

        map.put(productId, map.get(productId) + 1);

        session.setAttribute("cart", map);

        return "redirect:/cart";
    }
}
