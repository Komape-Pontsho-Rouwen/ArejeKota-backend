package com.pontshocodes.arejekota_backend.service;

import com.pontshocodes.arejekota_backend.repository.RestaurantRepository;
import com.pontshocodes.arejekota_backend.repository.VendorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTests {
    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private VendorRepository vendorRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    @Test
    void createRestaurant_(){

    }
}
