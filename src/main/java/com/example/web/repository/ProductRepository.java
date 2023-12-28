package com.example.web.repository;

import com.example.web.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByProductID(String productId);
    void deleteByProductID(String productID);

}
