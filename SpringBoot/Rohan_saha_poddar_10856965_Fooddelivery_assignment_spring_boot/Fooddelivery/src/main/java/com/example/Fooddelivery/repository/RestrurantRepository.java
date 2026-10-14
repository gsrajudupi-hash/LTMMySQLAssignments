package com.example.Fooddelivery.repository;

import com.example.Fooddelivery.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestrurantRepository extends
        JpaRepository<Restaurant ,Long> {
}
