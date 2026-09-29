package com.pontshocodes.arejekota_backend.service;

import com.pontshocodes.arejekota_backend.entity.Vendor; //Importing vendor because the test handles vendor object
import com.pontshocodes.arejekota_backend.entity.VendorStatus;
import com.pontshocodes.arejekota_backend.repository.VendorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class VendorServiceTest {

    //fake helpers for the unit test
    @Mock private VendorRepository vendorRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtService jwtService;

    @InjectMocks
    //the real class that we are testing
    private VendorService vendorService;

    @Test
    void register_savesVendorAsPending() {
       Vendor result = vendorService.register("Matshidiso", "Masedi", "mama@gmail.com", "081234567",
                "Mama's Kitchen", "23 Marshal Street");

        //Verifying if the save method was called(One of the important calls in the method)
        verify(vendorRepository).save(result);

        //Assert: Claims things that must be true if the  claim fails the test

        assertEquals(VendorStatus.PENDING, result.getStatus());
        assertFalse (result.isActive());
        assertNull(result.getApprovedAt());
    }

    @Test
    void approveVendor_activatesPendingVendors(){
        Vendor vendor = new Vendor("Matshidiso","Masedi","mam@gmail.com","081234567","Mama's Kitchen"," 23 Marshal Street");

        //Check if the fake repository asked for the vendor id
        when(vendorRepository.findById(1L)).thenReturn(Optional.of(vendor));

        when(passwordEncoder.encode(anyString())).thenReturn("hashed");

        Vendor result = vendorService.approveVendor((1L));


        //Asserting the changes approval is supposed to make

        assertEquals(VendorStatus.APPROVED,result.getStatus());

        assertTrue(result.isActive());
        assertTrue(result.isPasswordChangeRequired());

    }
    @Test
    void
}






















