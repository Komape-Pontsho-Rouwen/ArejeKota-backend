package com.pontshocodes.arejekota_backend.service;
import com.pontshocodes.arejekota_backend.entity.Admin;
import com.pontshocodes.arejekota_backend.entity.Vendor;
import com.pontshocodes.arejekota_backend.entity.VendorStatus;
import com.pontshocodes.arejekota_backend.repository.AdminRepository;
import com.pontshocodes.arejekota_backend.repository.VendorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final VendorRepository vendorRepository;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder , JwtService jwtService,VendorRepository vendorRepository ,PasswordGenerator passwordGenerator){
        this.adminRepository=adminRepository;
        this.passwordEncoder=passwordEncoder;
        this.jwtService=jwtService;
        this.vendorRepository=vendorRepository;


    }
    public String login (String email , String password){
        if(email==null || email.isBlank()) {
            throw new IllegalArgumentException("Email can not be empty");
        }
        if(password==null || password.isBlank()){
            throw new IllegalArgumentException("Password can not be empty");
        }

        Optional<Admin> result = adminRepository.findByEmail(email);
        Admin admin;
        if(result.isPresent()){
            admin = result.get();
        }
        else{
            throw new IllegalArgumentException("No admin account found for this email");
        }
        if(!admin.isActive()){
            throw new IllegalArgumentException("Admin account is inactive");
        }

        if(!passwordEncoder.matches(password , admin.getPasswordHash())){
            throw new IllegalArgumentException("Invalid password");
        }
        return jwtService.generateToken(admin.getEmail(),"ADMIN");
    }

    }


