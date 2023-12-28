package com.example.web.repository;

import com.example.web.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUserID(String userid);
    User findByUsername(String username);
    User findByEmail(String email);
    User findByRole(String role);
}
