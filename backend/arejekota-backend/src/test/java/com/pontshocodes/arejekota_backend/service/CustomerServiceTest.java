package com.pontshocodes.arejekota_backend.service;


import com.pontshocodes.arejekota_backend.repository.CustomerRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {


    //@Mock private final CustomerRepository customerRepository;
   // @Mock private final PasswordEncoder passwordEncoder;
   // @Mock private final JwtService jwtService;
    //@Mock private final OtpService otpService;


    @InjectMocks
    private CustomerService customerService;
}
