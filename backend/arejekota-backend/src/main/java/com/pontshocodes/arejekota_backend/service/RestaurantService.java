package com.pontshocodes.arejekota_backend.service;

import com.pontshocodes.arejekota_backend.entity.Restaurant;
import com.pontshocodes.arejekota_backend.entity.Vendor;
import com.pontshocodes.arejekota_backend.entity.VendorStatus;
import com.pontshocodes.arejekota_backend.repository.RestaurantRepository;
import com.pontshocodes.arejekota_backend.repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;


@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final VendorRepository vendorRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, VendorRepository vendorRepository) {
        this.restaurantRepository = restaurantRepository;
        this.vendorRepository = vendorRepository;
    }

    public Restaurant createRestaurant(String workEmail, String name, String description, String address, String phoneNumber, String imageUrl, LocalTime openingTime, LocalTime closingTime) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Restaurant name can not be empty.");
        }
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Address can not be empty.");
        }
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number can not be empty.");
        }
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new IllegalArgumentException("Please provide an image.");
        }
        if (openingTime == null || closingTime == null) {
            throw new IllegalArgumentException("Opening and closing times are required.");
        }

        Vendor vendor = vendorRepository.findByWorkEmail(workEmail)
                .orElseThrow(() -> new IllegalArgumentException("Vendor not found"));

        if (vendor.getStatus() != VendorStatus.APPROVED) {
            throw new IllegalArgumentException("Vendor must be approved to create a restaurant.");
        }
        if (!vendor.isActive()) {
            throw new IllegalArgumentException("Vendor account is not active.");
        }
        if (restaurantRepository.existsByVendor(vendor)) {
            throw new IllegalArgumentException("This vendor already has a restaurant.");
        }

        Restaurant restaurant = new Restaurant(name, description, address, phoneNumber,
                imageUrl, openingTime, closingTime, vendor);

        return restaurantRepository.save(restaurant);
    }
}



