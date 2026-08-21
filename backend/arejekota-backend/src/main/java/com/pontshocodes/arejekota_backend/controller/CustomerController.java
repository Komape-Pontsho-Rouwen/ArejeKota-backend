package com.pontshocodes.arejekota_backend.controller;


import com.pontshocodes.arejekota_backend.dto.*;
import com.pontshocodes.arejekota_backend.entity.Customer;
import com.pontshocodes.arejekota_backend.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    //Creating my first end point
    @PostMapping("/register")
    public ResponseEntity<String> register (@RequestBody CustomerRegistrationRequest request){
        customerService.registerCustomer(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword()
        );
        return ResponseEntity.ok("Registration Successful.Please check you email for your OTP");

    }
    @PostMapping("/activate")
    public ResponseEntity<String> activate (@RequestBody ActivateAccountRequest request){
        customerService.activateAccount(
                request.getEmail(),
                request.getSubmittedCode()
        );
        return ResponseEntity.ok("Account Activated.You can log in and enjoy your ordering!");
    }
    @PostMapping("/resend-otp")
    public ResponseEntity<String> resendOtp (@RequestBody ResendOtpRequest request){
        customerService.resendOtp(
                request.getEmail()
        );
        return ResponseEntity.ok("OTP resent successfully.Please check your email");
    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> requestPassword(@RequestBody PasswordResetRequest request){
        customerService.requestPasswordReset(
                request.getEmail()
        );
        return ResponseEntity.ok("Password reset OTP sent.Please check your emails.");
    }
    @PostMapping("/confirm-password-reset")
    public ResponseEntity<String> confirmPasswordReset(@RequestBody ConfirmPasswordResetRequest request){
        customerService.confirmPasswordReset(
                request.getEmail(),
                request.getSubmittedCode(),
                request.getNewPassword()
        );
        return ResponseEntity.ok("Password Request was successful.You can log in.");
    }
    @PutMapping("/update")
    public ResponseEntity<String> updateCustomer(@RequestBody UpdateCustomerRequest request){
        customerService.updateCustomer(
                request.getEmail(),
                request.getNewFirstName(),
                request.getNewLastName()
        );
        return ResponseEntity.ok("Account Updated successfully.");
    }
    @DeleteMapping("/delete-account")
    public ResponseEntity<String> deleteAccount(@RequestBody DeleteAccountRequest request){
        customerService.deleteAccount(
                request.getEmail(),
                request.getSubmittedCode()
        );
        return ResponseEntity.ok("Account deleted successfully.");


    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = customerService.login(
                request.getEmail(),
                request.getPassword()
        );
        return ResponseEntity.ok(token);
    }
}
