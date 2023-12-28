package com.example.web.service;

import com.example.web.model.Cart;
import com.example.web.model.Checkout;
import com.example.web.model.Product;
import com.example.web.repository.CartRepository;
import com.example.web.repository.CheckoutRepository;
import com.example.web.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CheckoutService {
    @Autowired
    private CheckoutRepository checkoutRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    public void addOrder(String cartID, String userID, int total, int quantity,String dateOrder){
        Checkout order = new Checkout();
        int countOrder = (int) checkoutRepository.count();

        order.setCheckoutID("checkout" + countOrder);
        order.setCartID(cartID);
        order.setUserID(userID);
        order.setTotal(total);
        order.setOrderdate(dateOrder);
        order.setQuantity(quantity);
        checkoutRepository.save(order);
    }
    public List<Checkout> getAllCheckoutsByUserID(String userID) {
        // Lấy danh sách đơn hàng từ repository
        List<Checkout> checkouts = checkoutRepository.findByUserID(userID);

        // Duyệt qua danh sách đơn hàng để thêm thông tin sản phẩm
        for (Checkout checkout : checkouts) {
            // Lấy thông tin từ bảng cart dựa trên cartID
            Cart cart = cartRepository.findByCartID(checkout.getCartID());

            if (cart != null) {
                // Lấy thông tin sản phẩm từ bảng product dựa trên productID
                Product product = productRepository.findByProductID(cart.getProductID());

                if (product != null) {
                    // Đặt tên sản phẩm vào đơn hàng
                    checkout.setProductName(product.getName());
                }
            }
        }

        return checkouts;
    }
    public List<Checkout> getAllChecks() {
        List<Checkout> checkouts = checkoutRepository.findAll();
        for (Checkout checkout : checkouts) {
            Cart cart = cartRepository.findByCartID(checkout.getCartID());
            if (cart != null) {
                Product product = productRepository.findByProductID(cart.getProductID());

                if (product != null) {
                    checkout.setProductName(product.getName());
                }
            }
        }
        return checkouts;
    }
    public int totalQuantity() {
        List<Checkout> checkouts = checkoutRepository.findAll();
        int totalQuantity = 0;

        for (Checkout checkout : checkouts) {
            totalQuantity += checkout.getQuantity();
        }
        return totalQuantity;
    }
    public int totalPrice() {
        List<Checkout> checkouts = checkoutRepository.findAll();
        int totalPrice = 0;

        for (Checkout checkout : checkouts) {
            totalPrice += checkout.getTotal();
        }

        return totalPrice;
    }

}
