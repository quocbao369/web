package com.example.web.controller;

import com.example.web.model.Checkout;
import com.example.web.service.CheckoutService;
import com.example.web.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeAdminController {
    @Autowired
    private CheckoutService checkoutService;
    @Autowired
    private UserService userService;
    @Autowired
    private HttpSession session;

    @GetMapping("/admin")
    public String home(Model model) {
        int quantity = checkoutService.totalQuantity();
        int totalPrice = checkoutService.totalPrice();
        long countUser = userService.countUsers();
        // Lấy danh sách đơn hàng từ service
        List<Checkout> historysold = checkoutService.getAllChecks();

        model.addAttribute("historysold", historysold);
        model.addAttribute("quantity",quantity);
        model.addAttribute("totalPrice",totalPrice);
        model.addAttribute("countUser",countUser);
        return "admin/homeAdmin";
    }
}
