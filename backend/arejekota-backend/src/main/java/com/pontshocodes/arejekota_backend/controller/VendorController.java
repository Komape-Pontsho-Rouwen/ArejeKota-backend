package com.pontshocodes.arejekota_backend.controller;

import com.pontshocodes.arejekota_backend.dto.LoginRequest;
import com.pontshocodes.arejekota_backend.dto.VendorRegistrationRequest;
import com.pontshocodes.arejekota_backend.service.VendorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vendor")
public class VendorController {
    private final VendorService vendorService;

    public VendorController(VendorService vendorService){
        this.vendorService = vendorService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<String> register (@RequestBody VendorRegistrationRequest request){
        vendorService.register(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPhoneNumber(),
                request.getBusinessName(),
                request.getAddress()
        );
        return ResponseEntity.ok("Registration successful.Please wait for admin approval.");
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request){
        String token = vendorService.login(
                request.getWorkEmail(),
                request.getPassword()

        );
        return ResponseEntity.ok(token);
    }
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request){
        String result = vendorService.login(
                request.getWorkEmail(),
                request.getNewPassowrd()
        );
        return ResponseEntity(result);

    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordRequestRequest request){
        vendorService.resetPassword(
                request.getEmail()
        );
        return ResponseEntity.ok("OTP sent.Please check your email.");
    }

    @PostMapping("/confirm-password-reset")
    public ReponseEntity<String> confirmPasswordReset (@RequestBody ConfirmPasswordResetRequest request){
        String result = vendorService.confirmPasswordReset(
                request.getEmail(),
                request.getSubmittedCode(),
                request.getNewPassword()
        );
        return ResponseEntity.ok(result);
    }



}
