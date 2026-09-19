package com.pontshocodes.arejekota_backend.config;


import com.pontshocodes.arejekota_backend.entity.Admin;
import com.pontshocodes.arejekota_backend.repository.AdminRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final Environment env;


    public AdminSeeder(AdminRepository adminRepository , PasswordEncoder passwordEncoder , Environment env){
        this.adminRepository = adminRepository;
        this.passwordEncoder=passwordEncoder;
        this.env = env;
    }
    @Override
    public void run(String...args){
        if(adminRepository.count() == 0){
            Admin admin = new Admin(
                    env.getProperty("admin.seed.email"),
                    passwordEncoder.encode(env.getProperty("admin.seed.passwordHash"))
            );

            adminRepository.save(admin);

        }
    }
}
