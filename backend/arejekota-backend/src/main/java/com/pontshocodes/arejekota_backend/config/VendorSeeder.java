package com.pontshocodes.arejekota_backend.config;


import com.pontshocodes.arejekota_backend.entity.Vendor;
import com.pontshocodes.arejekota_backend.repository.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class VendorSeeder implements CommandLineRunner {
    private final VendorRepository vendorRepository;
    private final PasswordEncoder passwordEncoder;
    private final Environment env;


    public VendorSeeder(VendorRepository vendorRepository ,PasswordEncoder passwordEncoder ,Environment env){
        this.vendorRepository=vendorRepository;
        this.passwordEncoder=passwordEncoder;
        this.env = env;
    }
    //Environment Variables
    @Override
    public void run (String...args){
        if(vendorRepository.count()==0){
            Vendor vendor = new Vendor(
                    env.getProperty("vendor.seed.business-name"),
                    env.getProperty("vendor.seed.address"),
                    env.getProperty("vendor.seed.email"),
                    passwordEncoder.encode(env.getProperty("vendor.seed.password"))

            );
            vendorRepository.save(vendor);
    }
        }
    }



