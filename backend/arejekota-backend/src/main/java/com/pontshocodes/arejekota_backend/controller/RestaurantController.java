package com.pontshocodes.arejekota_backend.controller;

import com.pontshocodes.arejekota_backend.dto.CreateRestaurantRequest;
import com.pontshocodes.arejekota_backend.dto.RestaurantResponse;
import com.pontshocodes.arejekota_backend.entity.Restaurant;
import com.pontshocodes.arejekota_backend.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendor")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    // Temporary: the work email comes from a header until security reads it from the JWT
    @PostMapping("/restaurant")
    public ResponseEntity<RestaurantResponse> createRestaurant(
            @RequestHeader("X-Work-Email") String workEmail,
            @RequestBody CreateRestaurantRequest request) {

        Restaurant restaurant = restaurantService.createRestaurant(
                workEmail,
                request.getName(),
                request.getDescription(),
                request.getAddress(),
                request.getPhoneNumber(),
                request.getImageUrl(),
                request.getOpeningTime(),
                request.getClosingTime());

        RestaurantResponse response = new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getDescription(),
                restaurant.getAddress(),
                restaurant.getPhoneNumber(),
                restaurant.getImageUrl(),
                restaurant.getOpeningTime(),
                restaurant.getClosingTime());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}