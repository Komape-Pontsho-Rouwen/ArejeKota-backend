package com.pontshocodes.arejekota_backend.repository;

import com.pontshocodes.arejekota_backend.entity.OtpCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpCodeRepository extends JpaRepository<OtpCode, Long> {
    Optional<OtpCode> findByTopByEmailOrderByIdDesc(String email);
}
