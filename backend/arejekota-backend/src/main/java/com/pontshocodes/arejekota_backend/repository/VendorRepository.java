package com.pontshocodes.arejekota_backend.repository;

import com.pontshocodes.arejekota_backend.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor,Long> {
    Optional<Vendor> findByEmail(String email);
    boolean existsByEmail(String email);
}
