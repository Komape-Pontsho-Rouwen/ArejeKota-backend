package com.pontshocodes.arejekota_backend.config;


import com.pontshocodes.arejekota_backend.entity.Vendor;
import com.pontshocodes.arejekota_backend.repository.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class VendorSeeder implements CommandLineRunner {
    private final VendorRepository vendorRepository;
    private final PasswordEncoder passwordEncoder;

    public VendorSeeder(VendorRepository vendorRepository ,PasswordEncoder passwordEncoder){
        this.vendorRepository=vendorRepository;
        this.passwordEncoder=passwordEncoder;
    }
    @Override
    public void run (String...args){
        if(vendorRepository.count() ==0){
            Vendor vendor = new Vendor(
                    "ArejeKota",
                    "45 Kevin Street Polokwane",
                    "owner@arejekota.co.za",
                    passwordEncoder.encode("ArejeKota2026@")

            );
        }
    }

}

