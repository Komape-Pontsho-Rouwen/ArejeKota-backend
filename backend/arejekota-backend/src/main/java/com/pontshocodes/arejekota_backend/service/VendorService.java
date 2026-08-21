package com.pontshocodes.arejekota_backend.service;


import com.pontshocodes.arejekota_backend.entity.Vendor;
import com.pontshocodes.arejekota_backend.repository.VendorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendorService {
    private final VendorRepository vendorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public VendorService(VendorRepository vendorRepository,PasswordEncoder passwordEncoder ,JwtService jwtService){
        this.vendorRepository=vendorRepository;
        this.passwordEncoder=passwordEncoder;
        this.jwtService=jwtService;

    }
    public String login(String email , String password){

        Optional<Vendor> result = vendorRepository.findByEmail(email);
        Vendor vendor;
        if(result.isPresent()){
            vendor = result.get();
        }
        else{
            throw new IllegalArgumentException("Invalid email or password");
        }
        if(!vendor.isActive()){
            throw new IllegalArgumentException("Vendor account is inactive");
        }

        if(!passwordEncoder.matches(password , vendor.getPasswordHash())){
            throw new IllegalArgumentException("Invalid email or password");
        }
        return jwtService.generateToken((vendor.getEmail()),"VENDOR");
    }
}
