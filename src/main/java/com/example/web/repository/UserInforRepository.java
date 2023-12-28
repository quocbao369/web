package com.example.web.repository;

import com.example.web.model.InforUser;
import org.springframework.data.repository.CrudRepository;

public interface UserInforRepository extends CrudRepository<InforUser, Long> {
    InforUser findByPhone(String phone);
    InforUser findByUserID(String userid);
}
