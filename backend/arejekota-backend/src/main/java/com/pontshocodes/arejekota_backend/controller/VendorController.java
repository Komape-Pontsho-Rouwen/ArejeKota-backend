package com.pontshocodes.arejekota_backend.controller;

import com.pontshocodes.arejekota_backend.dto.*;
import com.pontshocodes.arejekota_backend.service.VendorService;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendor")
public class VendorController {
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
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
        return ResponseEntity.ok("Registration successful.Please wait for admin approval");
    }
    @PostMapping("/login")
    public ResponseEntity<VendorLoginResponse> login (@RequestBody LoginRequest request){
        VendorLoginResponse response = vendorService.login(
                request.getEmail(),
                request.getPassword()
        );
        return ResponseEntity.ok(response);

    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword (@RequestBody PasswordResetRequest request){
        vendorService.resetPassword(
                request.getEmail()
        );
        return ResponseEntity.ok("Password reset OTP sent.Please check your emails.");

    }
    @PostMapping("/confirm-password-reset")
    public ResponseEntity<String>confirmPasswordReset(@RequestBody ConfirmPasswordResetRequest request){
        String result =vendorService.confirmPasswordReset(
                request.getEmail(),
                request.getSubmittedCode(),
                request.getNewPassword()
        );
        return ResponseEntity.ok(result);

    }
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody VendorChangePasswordRequest request,
                                                 Authentication authentication){
        String workEmail = authentication.getName();
        String result = vendorService.changePassword(
                workEmail ,request.getCurrentPassword(),
                request.getNewPassword()
        );

        return ResponseEntity.ok(result);
    }

}


