package com.example.web.repository;

import com.example.web.model.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParameterRepository extends JpaRepository<Parameter,Long> {
    Parameter findByParameterID(String parameterID);
    void deleteByParameterID(String parameterID);

}
