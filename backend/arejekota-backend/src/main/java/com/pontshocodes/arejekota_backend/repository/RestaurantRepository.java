package com.pontshocodes.arejekota_backend.repository;

import com.pontshocodes.arejekota_backend.entity.Restaurant;
import com.pontshocodes.arejekota_backend.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {


    //Searching a restaurant table associated with the specified vendor id
    Optional<Restaurant> findByVendor(Long vendorId);
    boolean existsByVendor(Vendor vendor);
}
