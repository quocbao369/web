package com.example.web.controller;

import com.example.web.dto.ProductInfor;
import com.example.web.model.Parameter;
import com.example.web.model.Product;
import com.example.web.service.ParameterService;
import com.example.web.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ParameterService parameterService;


    @GetMapping("/products")
    public String showAllProducts(Model model)
    {
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("products", productList);
        return "home";
    }
    @GetMapping("/product/{id}")
    public String getProductDetails(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        String parameterID = product.getParameterID();
        Parameter parameter = parameterService.getParameter(parameterID);

        // Thực hiện các thao tác khác nếu cần
        model.addAttribute("product", product);
        model.addAttribute("parameter", parameter);

        return "product/product-details"; // Trả về view hiển thị chi tiết sản phẩm
    }
    @GetMapping("/products/{MSP}")
    public String getProductDetails(@PathVariable String MSP, Model model) {
        Product product = productService.getProductById(MSP);

        // Thực hiện các thao tác khác nếu cần
        model.addAttribute("product", product);
        return "product/product-details"; // Trả về view hiển thị chi tiết sản phẩm
    }
    @GetMapping("/adminProduct")
    public String register(Model model) {
        model.addAttribute("productInfor", new ProductInfor());
        return "admin/managementProduct";
    }

    @PostMapping("/adminProduct")
    public String addProduct(ProductInfor productInfor, Model model) {
        if (!productService.isProductID(productInfor.getProductID())) {
            model.addAttribute("error", "ProductID already exists.");
            return "admin/managementProduct";
        }
        if (!parameterService.isParameterID(productInfor.getParameterID())) {
            model.addAttribute("error", "ParameterID already exists.");
            return "admin/managementProduct";
        }
        if (productService.addProduct(productInfor)){
            model.addAttribute("error", "Done!");
            return "admin/managementProduct";
        }
        return "admin/managementProduct";
    }
    @GetMapping("/admintable")
    public String tableProduct(Model model) {
        List<ProductInfor> productInfors = productService.getAllProductInfors();
        model.addAttribute("productInfors", productInfors);
        return "admin/productTable";
    }
    @GetMapping("/remove")
    public String removeProduct(@RequestParam String productID, @RequestParam String ID) {
        productService.removeProduct(productID,ID);
        return "redirect:/admintable";
    }

}
