package com.pontshocodes.arejekota_backend.service;

import com.pontshocodes.arejekota_backend.dto.MenuItemRequest;
import com.pontshocodes.arejekota_backend.dto.MenuItemResponse;
import com.pontshocodes.arejekota_backend.entity.MenuItem;
import com.pontshocodes.arejekota_backend.entity.Restaurant;
import com.pontshocodes.arejekota_backend.repository.MenuItemRepository;
import com.pontshocodes.arejekota_backend.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class MenuService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuService(MenuItemRepository menuItemRepository,
                       RestaurantRepository restaurantRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
    }

    
    @Transactional
    public MenuItemResponse addItem(String workEmail, MenuItemRequest request) {
        Restaurant restaurant = getRestaurantOf(workEmail);

        MenuItem item = new MenuItem(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getImageUrl(),
                request.getCategory(),
                restaurant);

        return toResponse(menuItemRepository.save(item));
    }

  
    // Private helpers

    //Needs fixing
    private Restaurant getRestaurantOf(String workEmail) {
        return restaurantRepository.findByVendor(vendor)
                .orElseThrow(() -> new IllegalArgumentException("You do not have a restaurant yet"));
    }


    
    private MenuItemResponse toResponse(MenuItem item) {
        return new MenuItemResponse(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getImageUrl(),
                item.getCategory(),
                item.isAvailable());
    }
}