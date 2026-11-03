package com.pontshocodes.arejekota_backend.service;

import com.pontshocodes.arejekota_backend.dto.MenuItemRequest;
import com.pontshocodes.arejekota_backend.dto.MenuItemResponse;
import com.pontshocodes.arejekota_backend.entity.MenuItem;
import com.pontshocodes.arejekota_backend.entity.Restaurant;
import com.pontshocodes.arejekota_backend.entity.Vendor;
import com.pontshocodes.arejekota_backend.repository.MenuItemRepository;
import com.pontshocodes.arejekota_backend.repository.RestaurantRepository;
import com.pontshocodes.arejekota_backend.repository.VendorRepository;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
public class MenuService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final VendorRepository vendorRepository;
    private final ImageStorageService imageStorageService;

    public MenuService(MenuItemRepository menuItemRepository,
                       RestaurantRepository restaurantRepository,
                    VendorRepository vendorRepository,
                ImageStorageService imageStorageService) {

        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
        this.vendorRepository = vendorRepository;
        this.imageStorageService = imageStorageService;
    }

    
    @Transactional
    public MenuItemResponse addItem(String workEmail, MenuItemRequest request ,MultipartFile image) {
        if (request.getName() == null || request.getName().isBlank()) {
        throw new IllegalArgumentException("Item name is required.");
        }
        if (request.getName().length() > 100) {
        throw new IllegalArgumentException("Item name can not be longer than 100 characters.");
        }
        if (request.getDescription() != null && request.getDescription().length() > 500) {
        throw new IllegalArgumentException("Description can not be longer than 500 characters.");
        }
        if (request.getPrice() == null) {
        throw new IllegalArgumentException("Price is required.");
        }
        if (request.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
        throw new IllegalArgumentException("Price must be greater than zero.");
        }
        if (request.getCategory() == null || request.getCategory().isBlank()) {
        throw new IllegalArgumentException("Category is required.");
        }
        if (request.getCategory().length() > 50) {
        throw new IllegalArgumentException("Category can not be longer than 50 characters.");
        }
        if(image==null || image.isEmpty()){
            throw new IllegalArgumentException("Item picture is required");
        }

        Restaurant restaurant = getRestaurantOf(workEmail);
        String imageUrl = imageStorageService.saveImage(image);

        MenuItem item = new MenuItem(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                imageUrl,
                request.getCategory(),
                restaurant);

        return toResponse(menuItemRepository.save(item));
    }

    //private helper class 

    private Restaurant getRestaurantOf(String workEmail){
        Vendor vendor = vendorRepository.findByWorkEmail(workEmail).orElseThrow(()-> new IllegalArgumentException("Vendor not found"));
        return restaurantRepository.findByVendor(vendor).orElseThrow(()-> new IllegalArgumentException("You do not have a restaurant yet"));

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
    