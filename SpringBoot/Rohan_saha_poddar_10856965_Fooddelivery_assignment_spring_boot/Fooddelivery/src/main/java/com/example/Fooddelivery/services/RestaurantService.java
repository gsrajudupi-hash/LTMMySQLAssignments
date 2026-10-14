package com.example.Fooddelivery.services;

//import com.foodexpress.dto.*;
// import com.foodexpress.entity.Restaurant;
// import com.foodexpress.exception.*;import com.foodexpress.repository.RestaurantRepository;
import com.example.Fooddelivery.dto.RestaurantRequest;
import com.example.Fooddelivery.dto.RestaurantResponse;
import com.example.Fooddelivery.entities.Restaurant;
import com.example.Fooddelivery.exception.BusinessException;
import com.example.Fooddelivery.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;import java.util.List;
@Service @RequiredArgsConstructor public class RestaurantService{
    private final RestaurantRepository repo;
    public RestaurantResponse create(RestaurantRequest r){return map(repo.save(Restaurant.builder().name(r.name()).location(r.location()).contactNumber(r.contactNumber()).active(r.active()).build()));}
    public List<RestaurantResponse> all(){return repo.findAll().stream().map(this::map).toList();}
    public RestaurantResponse get(Long id){return map(entity(id));}
    public RestaurantResponse update(Long id,RestaurantRequest r){Restaurant e=entity(id);e.setName(r.name());e.setLocation(r.location());e.setContactNumber(r.contactNumber());e.setActive(r.active());return map(repo.save(e));}
    public void delete(Long id){Restaurant e=entity(id);if(!e.getOrders().isEmpty()) throw new BusinessException("Restaurant with order history cannot be deleted");repo.delete(e);}
    public Restaurant entity(Long id){return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Restaurant not found: "+id));}
    private RestaurantResponse map(Restaurant e){return new RestaurantResponse(e.getRestaurantId(),e.getName(),e.getLocation(),e.getContactNumber(),e.isActive());}
}
