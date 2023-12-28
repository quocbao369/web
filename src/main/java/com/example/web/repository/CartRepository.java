package com.example.web.repository;

import com.example.web.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserID(String userid);

    Cart findByUserIDAndProductID(String userId, String productId);

    List<Cart> findByUserIDAndStatus(String userID, String status);

    Cart findByCartID(String cartID);
}