package com.pontshocodes.arejekota_backend.repository;

import com.pontshocodes.arejekota_backend.entity.Customer;
import com.pontshocodes.arejekota_backend.entity.OtpCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpCodeRepository extends JpaRepository<OtpCode, Long> {
    //searching for an OTP belonging to this account
    Optional<OtpCode> findByCustomer(Customer customer);
    void deleteByCustomer(Customer customer );
}
