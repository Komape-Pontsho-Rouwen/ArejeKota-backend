package com.pontshocodes.arejekota_backend.repository;

import com.pontshocodes.arejekota_backend.entity.MenuItem;
import com.pontshocodes.arejekota_backend.entity.Vendor;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    Optional<MenuItem> findByVendor(Vendor vendor);

}