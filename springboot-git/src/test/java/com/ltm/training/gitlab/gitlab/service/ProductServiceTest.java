package com.ltm.training.gitlab.gitlab.service;

import com.ltm.training.gitlab.Service.ProductService;

import com.ltm.training.gitlab.model.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    private final ProductService service = new ProductService();

    @Test
    void testFindAll() {
        assertEquals(3, service.findAll().size());
    }

    @Test
    void testFindById() {
        Product p = service.findById(1L).orElseThrow();
        assertEquals("Keyboard", p.getName());
    }

    @Test
    void testUnknownId() {
        assertTrue(service.findById(999L).isEmpty());
    }
}
