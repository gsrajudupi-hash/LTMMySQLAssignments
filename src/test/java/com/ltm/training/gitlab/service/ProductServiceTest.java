package com.ltm.training.gitlab.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ltm.training.gitlab.model.Product;

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
