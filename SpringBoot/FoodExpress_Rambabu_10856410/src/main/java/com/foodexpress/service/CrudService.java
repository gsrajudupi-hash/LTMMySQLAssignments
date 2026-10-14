package com.foodexpress.service;

import com.foodexpress.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public class CrudService<T> {
    private final JpaRepository<T, Long> repo;
    private final String name;

    public CrudService(JpaRepository<T, Long> r, String n) {
        repo = r;
        name = n;
    }

    public T save(T x) {
        return repo.save(x);
    }

    public List<T> all() {
        return repo.findAll();
    }

    public T one(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException(name + " not found"));
    }

    public void delete(Long id) {
        repo.delete(one(id));
    }
}
