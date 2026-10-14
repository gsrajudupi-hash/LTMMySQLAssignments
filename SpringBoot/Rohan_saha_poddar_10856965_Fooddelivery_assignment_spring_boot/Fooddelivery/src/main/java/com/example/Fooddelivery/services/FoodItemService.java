package com.example.Fooddelivery.services;

import com.example.Fooddelivery.dto.FoodItemRequest;
import com.example.Fooddelivery.dto.FoodItemResponse;
//import com.foodexpress.dto.*;
//import com.foodexpress.entity.*;
// import com.foodexpress.exception.*;
// import com.foodexpress.repository.FoodItemRepository;
import com.example.Fooddelivery.entities.FoodItem;
import com.example.Fooddelivery.entities.Restaurant;
import com.example.Fooddelivery.exception.ResourceNotFoundException;
import com.example.Fooddelivery.repository.FoodItemRepository;
import lombok.RequiredArgsConstructor;import org.springframework.data.domain.*;import org.springframework.stereotype.Service;import java.util.List;
@Service @RequiredArgsConstructor public class FoodItemService{
    private final FoodItemRepository repo;
    private final RestaurantService restaurants;
    public FoodItemResponse create(FoodItemRequest r){
        Restaurant x=restaurants.entity(r.restaurantId());return map(repo.save(FoodItem.builder().name(r.name()).description(r.description()).price(r.price()).category(r.category()).available(r.available()).restaurant(x).build()));}
    public Page<FoodItemResponse> all(Pageable p){return repo.findAll(p).map(this::map);}
    public FoodItemResponse get(Long id){return map(entity(id));}
    public List<FoodItemResponse> category(String c){return repo.findByCategoryIgnoreCase(c).stream().map(this::map).toList();}
    public List<FoodItemResponse> menu(Long restaurantId){restaurants.entity(restaurantId);return repo.findByRestaurantRestaurantIdAndAvailableTrue(restaurantId).stream().map(this::map).toList();}
    public FoodItemResponse update(Long id,FoodItemRequest r){FoodItem f=entity(id);Restaurant x=restaurants.entity(r.restaurantId());f.setName(r.name());f.setDescription(r.description());f.setPrice(r.price());f.setCategory(r.category());f.setAvailable(r.available());f.setRestaurant(x);return map(repo.save(f));}
    public void delete(Long id){FoodItem f=entity(id);if(!f.getOrderItems().isEmpty()){f.setAvailable(false);repo.save(f);return;}repo.delete(f);}
    public FoodItem entity(Long id){return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Food item not found: "+id));}
    private FoodItemResponse map(FoodItem f){return new FoodItemResponse(f.getFoodItemId(),f.getName(),f.getDescription(),f.getPrice(),f.getCategory(),f.isAvailable(),f.getRestaurant().getRestaurantId());}
}
