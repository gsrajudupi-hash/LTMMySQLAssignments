package com.ltm.training.gitlab.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.ltm.training.gitlab.model.Product;

class ProductServiceTest {
	
	private final ProductService productService = new ProductService();

	
	
	@Test
	void testFindAll() {
		assertEquals(3, productService.findAll().size());
		
	}
	
	@Test
	void testFindById() {
		Product p = productService.findById(1L).orElseThrow();
		assertEquals("Keyboard", p.getName());
		assertEquals(new BigDecimal("49.99"), p.getPrice());
		assertTrue(productService.findById(99L).isEmpty());
	}
	
	void testUnknownId() {
		assertTrue(productService.findById(99L).isEmpty());
	}

}
