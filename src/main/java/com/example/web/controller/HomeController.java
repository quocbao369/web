package com.example.web.controller;

import com.example.web.model.Product;
import com.example.web.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }

        model.addAttribute("pageTitle", "Home Page");
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("products", productList);
        // Các dòng khác...
        return "home";
    }
}

