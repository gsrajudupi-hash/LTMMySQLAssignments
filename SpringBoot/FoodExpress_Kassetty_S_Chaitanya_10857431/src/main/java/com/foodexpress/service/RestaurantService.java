package com.foodexpress.service;

import com.foodexpress.entity.Restaurant;
import com.foodexpress.exception.ResourceNotFoundException;
import com.foodexpress.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
    private final RestaurantRepository repo;

    public RestaurantService(RestaurantRepository repo) {
        this.repo = repo;
    }

    public Restaurant create(Restaurant x) {
        return repo.save(x);
    }

    public List<Restaurant> all() {
        return repo.findAll();
    }

    public Restaurant get(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found: " + id));
    }

    public Restaurant update(Long id, Restaurant r) {
        Restaurant x = get(id);
        x.setName(r.getName());
        x.setLocation(r.getLocation());
        x.setContactNumber(r.getContactNumber());
        x.setActive(r.isActive());
        return repo.save(x);
    }

    public void delete(Long id) {
        repo.delete(get(id));
    }
}
