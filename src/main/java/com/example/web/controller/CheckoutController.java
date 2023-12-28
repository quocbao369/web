package com.example.web.controller;

import com.example.web.model.Cart;
import com.example.web.model.Product;
import com.example.web.service.CartService;
import com.example.web.service.CheckoutService;
import com.example.web.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class CheckoutController {
    @Autowired
    private CheckoutService orderService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private HttpSession session;

    @GetMapping("/order/checkout/{cartID}")
    public String showOrder(@PathVariable String cartID,Model model) {
        Cart cart = cartService.getCart(cartID);
        Product product = productService.getProductById(cart.getProductID());

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = currentDate.format(formatter);

        // Lấy thông tin đơn hàng từ dữ liệu của bạn
        String productName = product.getName();
        int totalAmount = cart.getTotal();

        // Truyền thông tin đơn hàng vào model
        model.addAttribute("productName", productName);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("paymentDate", formattedDate);
        model.addAttribute("cartID", cartID);
        model.addAttribute("quantity",cart.getStock());

        // Trả về trang checkout.html
        return "product/checkout";
    }
    @GetMapping("/order/confirmCheckout")
    public String confirmCheckout(@RequestParam String cartID, @RequestParam int total, @RequestParam int quantity, Model model) {
        String userID = (String) session.getAttribute("userid");

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateOrder = currentDate.format(formatter);

        orderService.addOrder(cartID,userID,total,quantity,dateOrder);
        cartService.updateStatus(cartID);
        // Sau khi xác nhận, có thể chuyển hướng đến trang cảm ơn hoặc trang chính
        return "redirect:/cart/cartAll";
    }
}
