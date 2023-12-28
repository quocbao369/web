package com.example.web.service;

import com.example.web.dto.ProductInfor;
import com.example.web.model.Parameter;
import com.example.web.model.Product;
import com.example.web.repository.ParameterRepository;
import com.example.web.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ParameterRepository parameterRepository;
    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }
    public Product getProductById(String productId) {
        return productRepository.findByProductID(productId);
    }
    public boolean addProduct(ProductInfor productInfor){
        Product product = new Product();
        Parameter parameter = new Parameter();

        product.setProductID(productInfor.getProductID());
        product.setParameterID(productInfor.getParameterID());
        product.setName(productInfor.getNameProduct());
        product.setPrice(productInfor.getPrice());
        product.setDescription(productInfor.getDescription());
        product.setImage(productInfor.getImage());
        productRepository.save(product);

        parameter.setParameterID(productInfor.getParameterID());
        parameter.setScreensize(productInfor.getScreensize());
        parameter.setProcessor(productInfor.getProcessor());
        parameter.setRam(productInfor.getRam());
        parameter.setStorage(productInfor.getStorage());
        parameter.setCamera(productInfor.getCamera());
        parameterRepository.save(parameter);

        return true;
    }
    public boolean isProductID(String productID){
        Product ID = productRepository.findByProductID(productID);
        return ID ==null;
    }
    public List<ProductInfor> getAllProductInfors() {
        List<Product> products = productRepository.findAll();
        List<ProductInfor> productInfors = new ArrayList<>();

        for (Product product : products) {
            ProductInfor productInfor = new ProductInfor();
            productInfor.setProductID(product.getProductID());
            productInfor.setParameterID(product.getParameterID());
            productInfor.setNameProduct(product.getName());
            productInfor.setPrice(product.getPrice());
            productInfor.setDescription(product.getDescription());
            productInfor.setStock(product.getStock());
            productInfor.setImage(product.getImage());

            // Lấy thông tin từ bảng Parameter
            Parameter parameter = parameterRepository.findByParameterID(product.getParameterID());
            if (parameter != null) {
                productInfor.setScreensize(parameter.getScreensize());
                productInfor.setProcessor(parameter.getProcessor());
                productInfor.setRam(parameter.getRam());
                productInfor.setStorage(parameter.getStorage());
                productInfor.setCamera(parameter.getCamera());
            }

            productInfors.add(productInfor);
        }

        return productInfors;
    }
    @Transactional
    public void removeProduct(String productID, String parameterID) {
        productRepository.deleteByProductID(productID);
        parameterRepository.deleteByParameterID(parameterID);
    }

}
