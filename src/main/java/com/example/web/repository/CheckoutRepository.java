package com.example.web.repository;

import com.example.web.model.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout,Long> {
    List<Checkout> findByUserID(String userid);
}
