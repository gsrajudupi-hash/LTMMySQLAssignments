package com.ltm.training.gitlab.controller;

import com.ltm.training.gitlab.Service.ProductService;
import com.ltm.training.gitlab.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Author  : 10856807
 * Date    : 26-10-2026
 * Time    : 15:54
 * Project : springboot-git
 */
@RestController
@RequestMapping(value="/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/hello")
    public String hello(){
        return "Welcome to the Spring Boot Git Lab API";
    }

    @GetMapping("/products")
    public List<Product> all(){
        return productService.findAll();
    }

    @GetMapping("/products/{id}")
    public Product byId(@PathVariable Long id){
        return productService.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found: "+id));
    }


}
