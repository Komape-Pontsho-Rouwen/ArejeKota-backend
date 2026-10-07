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
    @Mock private OtpService otpService;

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

        //set of instructions before the approval of the vendor
        Vendor vendor = new Vendor("Matshidiso","Masedi","mama@gmail.com","081234567","Mama's Kitchen"," 23 Marshal Street");

        //Setting up an answer in advance when this method gets called
        when(vendorRepository.findById(1L)).thenReturn(Optional.of(vendor));

        when(passwordEncoder.encode(anyString())).thenReturn("hashed");

        Vendor result = vendorService.approveVendor((1L));

        //Asserting the changes approval is supposed to make

        assertEquals(VendorStatus.APPROVED,result.getStatus());
        assertTrue(result.isActive());
        assertTrue(result.isPasswordChangeRequired());

    }
    @Test
    void login_returnsTokenForValidCredentials(){
        Vendor vendor = new Vendor("Matshidiso","Masedi","mama@gmail.com","081234567","Mama's Kitchen"," 23 Marshal Street");

        vendor.setWorkEmail("mamaskitchen01@arejekota.co.za");
        vendor.setPasswordHash("hashedPassword");

        //Tells the fake repos how to respond to the call made by login()

        when(vendorRepository.findByWorkEmail("mamaskitchen01@arejekota.co.za")).thenReturn(Optional.of(vendor));
        when(passwordEncoder.matches("Temp1234","hashedPassword")).thenReturn(true);
        when(jwtService.generateToken("mamaskitchen01@arejekota.co.za","VENDOR")).thenReturn("fake-jwt-token");


        String token = vendorService.login("mamaskitchen01@arejekota.co.za","Temp1234");
        assertEquals("fake-jwt-token" , token);

    }

    @Test
    void login_rejectsWrongPassword() {
        Vendor vendor = new Vendor("Matshidiso", "Masedi", "mama@gmail.com",
                "0812345678", "Mama's Kitchen", "23 Marshal Street");
        vendor.setWorkEmail("mamaskitchen01@arejekota.co.za");
        vendor.setPasswordHash("hashedPassword");

        when(vendorRepository.findByWorkEmail("mamaskitchen01@arejekota.co.za")).thenReturn(Optional.of(vendor));
        when(passwordEncoder.matches("WrongPassword", "hashedPassword")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () ->
                vendorService.login("mamaskitchen01@arejekota.co.za", "WrongPassword"));
    }

    @Test
    void  rejectVendor_rejectPendingVendor() {
        Vendor vendor = new Vendor("Matshidiso", "Masedi", "mama@gmail.com",
                "0812345678", "Mama's Kitchen", "23 Marshal Street");

        when(vendorRepository.findById(1L)).thenReturn(Optional.of(vendor));
        Vendor result = vendorService.rejectVendor(1L);

        assertEquals(VendorStatus.REJECTED , result.getStatus());
    }

    @Test
    void rejectVendor_rejectsNonPendingVendor(){
        Vendor vendor = new Vendor("Matshidiso", "Masedi", "mama@gmail.com",
                "0812345678", "Mama's Kitchen", "23 Marshal Street");
        vendor.setStatus(VendorStatus.APPROVED);
        when(vendorRepository.findById(1L)).thenReturn(Optional.of(vendor));

        assertThrows(IllegalArgumentException.class,()-> vendorService.rejectVendor(1L));

    }
    @Test
    void resetPassword_sendOtpForActiveVendor(){
        Vendor vendor = new Vendor();
        vendor.setEmail("mama@gmail.com");
        vendor.setActive(true);

        when(vendorRepository.findByEmail("mama@gmail.com")).thenReturn(Optional.of(vendor));

        vendorService.resetPassword("mama@gmail.com");

        verify(vendorRepository).findByEmail("mama@gmail.com");
        verify(otpService).generateOTP("mama@gmail.com");


    }
    @Test
    void resetPassword_throwsForInactiveVendorAccount(){
        Vendor vendor = new Vendor();
        vendor.setEmail("mama@gmail.com");
        vendor.setActive(false);

        when(vendorRepository.findByEmail("mama@gmail.com")).thenReturn(Optional.of(vendor));

        assertThrows(IllegalArgumentException.class , ()-> vendorService.resetPassword("mama@gmail.com"));
    }

    @Test
    void confirmPasswordReset_updatesPasswordAndConfirmsSuccess(){
        Vendor vendor = new Vendor();
        vendor.setEmail("mama@gmail.com");
        vendor.setWorkEmail("mamaskitchen@arejekota.co.za");
        vendor.setActive(true);

        when(vendorRepository.findByEmail("mama@gmail.com")).thenReturn(Optional.of(vendor));
        when(otpService.verifyOtp("mama@gmail.com","123456")).thenReturn(true);
        when(passwordEncoder.encode("NewPass123@")).thenReturn("hashed-new-password");


        String result= vendorService.confirmPasswordReset("mama@gmail.com","123456" ,"NewPass123@");



        assertEquals("hashed-new-password",vendor.getPasswordHash());
        assertFalse(vendor.isPasswordChangeRequired());
        verify(vendorRepository).save(vendor);

    }


}
























