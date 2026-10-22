package com.ltm.producthive.service;

import com.ltm.producthive.model.Product;
import com.ltm.producthive.repository.Productrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Author  : 10856807
 * Date    : 07-10-2026
 * Time    : 17:35
 * Project : product-restapi
 */

/*
 * @Service annotation is a stereotype annotation that marks a class as a service layer component.
 * It's one of the core annotations used to structure your application and enable dependency injection.
 *
 * The @Service annotation tells Spring that the annotated class contains business logic.
 * It's typically where you'll implement the core functionality of your application,
 * such as calculations, data retrieval, or external API interactions.
 */


@Service
public class ProductService {

    private final Productrepository productRepository;

    public ProductService(Productrepository productRepository) {
        super();
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    // Optional return type is to handle Null Pointer Exception
    public Optional<Product> getSingleProduct(long pid) {
        return productRepository.findById(pid);            //Invokes pre-defined method findById() of JPA repository
    }

    public void deleteProduct(long pid) {
        productRepository.deleteById(pid); //Invokes pre-defined method deleteById() of JPA repository
    }
}
