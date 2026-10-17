package com.ltm.producthive.repository;

import com.ltm.producthive.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author  : 10856807
 * Date    : 07-10-2026
 * Time    : 17:31
 * Project : product-restapi
 *
 * JPA Repository is mainly used for managing the data in a Spring Boot Application.
 * JpaRepository is particularly a JPA specific extension for Repository.
 * Jpa Repository contains the APIs for basic CRUD operations, the APIS for
 * pagination, and the APIs for sorting.
 */
public interface Productrepository extends JpaRepository<Product, Long> {
    /* Long is data type of @Id field of Product Class
     *
     * This interface has save(),findAll(),findById(),deleteById(),count()
       etc.. inbuilt methods of jpa repository for various database operations.

       This interface will be implemented by class automatically
     */
}
