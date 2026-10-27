package com.ltm.training.gitlab.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ltm.training.gitlab.model.Product;

@Service
public class ProductService {
	
	private final List<Product> products = List.of(
			new Product(1L,"Keyboard",new BigDecimal("52.99"),"InputDevices"),
			new Product(2L,"Mouse",new BigDecimal("19.99"),"InputDevices"),
			new Product(3L,"Monitor",new BigDecimal("189.00"),"OutputDevices"));
	
	public List<Product> findAll(){
		return products;
	}
 
	public Optional<Product> findById(Long id) {
		return products.stream().filter(p -> p.getId().equals(id)).findFirst();
	}

}
