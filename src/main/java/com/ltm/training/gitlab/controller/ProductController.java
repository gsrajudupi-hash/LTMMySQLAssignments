package com.ltm.training.gitlab.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ltm.training.gitlab.model.Product;
import com.ltm.training.gitlab.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
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
