package com.example.web.service;

import com.example.web.model.Parameter;
import com.example.web.repository.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParameterService {
    @Autowired
    private ParameterRepository parameterRepository;
    public Parameter getParameter(String parameterID){
        return parameterRepository.findByParameterID(parameterID);
    }
    public boolean isParameterID(String parameterID){
        Parameter ID = parameterRepository.findByParameterID(parameterID);
        return ID ==null;
    }
}
