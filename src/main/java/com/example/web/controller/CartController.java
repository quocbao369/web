package com.example.web.controller;

import com.example.web.dto.CartItem;
import com.example.web.model.Checkout;
import com.example.web.service.CartService;
import com.example.web.service.CheckoutService;
import com.example.web.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CheckoutService checkoutService;
    @Autowired
    private HttpSession session;

    @PostMapping("/add")
    public String addToCart(@RequestParam String productId, @RequestParam int stock, Principal principal) {
        String userid = (String) session.getAttribute("userid");
        cartService.addToCart(userid,productId,stock);
        return "redirect:/home";
    }
    @GetMapping("/cartAll")
    public String getCartByUserid( Model model) {
        String userid = (String) session.getAttribute("userid");
        List<CartItem> userCart = cartService.getAllCart(userid);
        model.addAttribute("userCart", userCart);
        return "product/cartList";
    }
    @GetMapping("/remove/{cartItemId}")
    public String removeFromCart(@PathVariable Long cartItemId) {
        cartService.removeFromCart(cartItemId);
        return "redirect:/cart/cartAll";
    }
    @GetMapping("/checkout/{cartItemId}")
    public String checkoutProduct(@PathVariable Long cartItemId) {

        cartService.removeFromCart(cartItemId);
        return "redirect:/cart/cartAll";
    }
    @GetMapping("/history")
    public String showOrderHistory(Model model) {
        String userID = (String) session.getAttribute("userid");
        // Lấy danh sách đơn hàng từ service
        List<Checkout> checkoutHistory = checkoutService.getAllCheckoutsByUserID(userID);
        model.addAttribute("checkoutHistory", checkoutHistory);
        // Trả về view để hiển thị
        return "product/orderhistory";
    }
}
