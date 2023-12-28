package com.example.web.service;

import com.example.web.dto.CartItem;
import com.example.web.model.Cart;
import com.example.web.model.Product;
import com.example.web.repository.CartRepository;
import com.example.web.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartRepository cartRepository;
    public void addToCart(String userID, String productID, int stock) {
        // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chưa
        Cart existingCartItem = cartRepository.findByUserIDAndProductID(userID, productID);

        if (existingCartItem != null) {
            // Nếu đã tồn tại
            if ("undone".equals(existingCartItem.getStatus())) {
                // Nếu status là undone, tăng số lượng lên
                existingCartItem.setStock(existingCartItem.getStock() + stock);
                existingCartItem.setTotal(existingCartItem.getTotal() + (getProductPrice(productID) * stock));
                cartRepository.save(existingCartItem);
            } else {
                // Nếu status là done, thêm mới sản phẩm đó và cart
                Cart cart = new Cart();
                int countCart = (int) cartRepository.count();

                cart.setCartID("cart" + countCart);
                cart.setUserID(userID);
                cart.setProductID(productID);
                cart.setStock(stock);
                cart.setTotal(getProductPrice(productID) * stock);
                cart.setStatus("undone");
                cartRepository.save(cart);
            }
        } else {
            // Nếu productID không tồn tại, thêm mới sản phẩm vào giỏ hàng
            Cart cart = new Cart();
            int countCart = (int) cartRepository.count();

            cart.setCartID("cart" + countCart);
            cart.setUserID(userID);
            cart.setProductID(productID);
            cart.setStock(stock);
            cart.setTotal(getProductPrice(productID) * stock);
            cart.setStatus("undone");
            cartRepository.save(cart);
        }
    }


    public List<CartItem> getAllCart(String userid){
        List<Cart> userCart = cartRepository.findByUserIDAndStatus(userid, "undone");
        List<CartItem> cartItems = new ArrayList<>();

        for (Cart cart : userCart) {
            Product product = productService.getProductById(cart.getProductID());

            if (product != null) {
                CartItem cartItem = new CartItem();
                cartItem.setId(cart.getId());
                cartItem.setCartID(cart.getCartID());
                cartItem.setProductID(cart.getProductID());
                cartItem.setName(product.getName());
                cartItem.setPrice(product.getPrice());
                cartItem.setStock(cart.getStock());
                cartItem.setTotal(cart.getTotal());
                cartItems.add(cartItem);
            }
        }

        return cartItems;
    }
    public void removeFromCart(Long cartItemId) {
        cartRepository.deleteById(cartItemId);
    }
    private int getProductPrice(String productId) {
        // Lấy giá của sản phẩm từ Repository của Product
        Product product = productRepository.findByProductID(productId);

        return  product.getPrice() ;
    }
    public Cart getCart(String cartID){
        return cartRepository.findByCartID(cartID);
    }
    public void updateStatus(String cartID){
        Cart cart = cartRepository.findByCartID(cartID);
            cart.setStatus("done");
            cartRepository.save(cart);
    }
}
