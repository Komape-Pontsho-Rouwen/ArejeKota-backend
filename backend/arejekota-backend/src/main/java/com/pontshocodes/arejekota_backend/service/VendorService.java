package com.pontshocodes.arejekota_backend.service;


import com.pontshocodes.arejekota_backend.dto.PendingVendorResponse;
import com.pontshocodes.arejekota_backend.dto.VendorApprovalResponse;
import com.pontshocodes.arejekota_backend.dto.VendorLoginResponse;
import com.pontshocodes.arejekota_backend.entity.Vendor;
import com.pontshocodes.arejekota_backend.entity.VendorStatus;
import com.pontshocodes.arejekota_backend.exeption.EmailAlreadyExistsException;
import com.pontshocodes.arejekota_backend.repository.VendorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final OtpService otpService;


    private static final String EMAIL_DOMAIN = "@arejekota.co.za";
    private static final String PASSWORD_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz23456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public VendorService (VendorRepository vendorRepository, PasswordEncoder passwordEncoder, JwtService jwtService, OtpService otpService) {
        this.vendorRepository = vendorRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.otpService = otpService;

    }

    public Vendor register(String firstName, String lastName, String email, String cellPhone, String businessName, String address) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name can not be empty");

        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name can not be empty");

        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email can not be empty");
        }
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailPattern)) {
            throw new IllegalArgumentException("Please enter a valid email");
        }
        if (cellPhone == null || cellPhone.isBlank()) {
            throw new IllegalArgumentException("Cellphone can not be empty");

        }
        if (businessName == null || businessName.isBlank()) {
            throw new IllegalArgumentException("Business name can not be empty");
        }
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Address can not be empty");
        }
        if (vendorRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("A vendor application already exists for this email");
        }
        Vendor vendor = new Vendor(firstName, lastName, email, cellPhone, businessName, address);
        vendorRepository.save(vendor);

        return vendor;
    }

    public VendorApprovalResponse approveVendor(Long vendorId) {
        Optional<Vendor> result = vendorRepository.findById(vendorId);
        Vendor vendor;
        if (result.isPresent()) {
            vendor = result.get();
        } else {
            throw new IllegalArgumentException("No vendor Application found");
        }
        if (vendor.getStatus() != VendorStatus.PENDING) {
            throw new IllegalArgumentException("Only pending vendors can be approved");

        }
        String workEmail = generateVendorWorkEmail(vendor.getBusinessName());
        String tempPassword = generateTemporaryPassword();

        vendor.setWorkEmail(workEmail);
        vendor.setPasswordHash(passwordEncoder.encode(tempPassword));
        vendor.setStatus(VendorStatus.APPROVED);
        vendor.setActive(true);
        vendor.setPasswordChangeRequired(true);
        vendor.setApprovedAt(LocalDateTime.now());

        vendorRepository.save(vendor);
        VendorApprovalResponse response = new VendorApprovalResponse(vendor.getId(), workEmail, tempPassword);

        return response;
    }

    private String generateVendorWorkEmail(String businessName) {
        String base = businessName.trim().toLowerCase().replaceAll("[^a-z0-9]", "");
        int suffix = 1;
        String candidate;
        do {
            candidate = base + String.format("%02d", suffix) + EMAIL_DOMAIN;
            suffix++;

        } while (vendorRepository.existsByWorkEmail(candidate));
        return candidate;
    }

    private String generateTemporaryPassword() {
        StringBuilder passwordBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            passwordBuilder.append(PASSWORD_CHARS.charAt(RANDOM.nextInt(PASSWORD_CHARS.length())));
        }
        return passwordBuilder.toString();

    }
    public List<PendingVendorResponse> listPendingVendors() {
        List<Vendor> pendingVendors = vendorRepository.findByStatus(VendorStatus.PENDING);
        List<PendingVendorResponse> responses = new ArrayList<>();
        for (Vendor vendor : pendingVendors) {
            responses.add(new PendingVendorResponse(
                    vendor.getId(),
                    vendor.getFirstName(),
                    vendor.getLastName(),
                    vendor.getEmail(),
                    vendor.getCellPhone(),
                    vendor.getBusinessName(),
                    vendor.getAddress()));
        }
        return responses;
    }

    public Vendor rejectVendor(Long vendorId) {
        Optional<Vendor> result = vendorRepository.findById(vendorId);
        Vendor vendor;
        if (result.isPresent()) {
            vendor = result.get();
        } else {
            throw new IllegalArgumentException("No vendor application found for this id");
        }
        if (vendor.getStatus() != VendorStatus.PENDING) {
            throw new IllegalArgumentException("Only pending vendors can be rejected");
        }
        vendor.setStatus(VendorStatus.REJECTED);
        vendorRepository.save(vendor);
        return vendor;
    }



    public List<Vendor> listRejectedVendors() {
        return vendorRepository.findByStatus(VendorStatus.REJECTED);
    }

    public VendorLoginResponse login(String workEmail, String password) {
        if (workEmail == null || workEmail.isBlank()) {
            throw new IllegalArgumentException("Email can not be empty");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password can not be empty");
        }
        Vendor vendor = vendorRepository.findByWorkEmail(workEmail)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email"));
        if (!passwordEncoder.matches(password, vendor.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid Password");
        }

        String token = jwtService.generateToken(vendor.getWorkEmail(), "VENDOR");
        return new VendorLoginResponse(token ,vendor.isPasswordChangeRequired());
    }

    //Change password after logging in using temporary password

    public String changePassword(String workEmail,String currentPassword, String newPassword) {
        if (workEmail == null || workEmail.isBlank()) {
            throw new IllegalArgumentException("Email can not be empty.");
        }
        if(currentPassword ==null || currentPassword.isBlank()){
            throw new IllegalArgumentException("Current password can not be empty");
        }
        if (newPassword == null || newPassword.isBlank()) {
            throw new IllegalArgumentException("Password can not be empty.");
        }

        Vendor vendor = vendorRepository.findByWorkEmail(workEmail).orElseThrow(() -> new IllegalArgumentException("Vendor not found"));
        if(!passwordEncoder.matches(currentPassword ,vendor.getPasswordHash())){
            throw new IllegalArgumentException("Current Password is incorrect");
        }
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$";
        if(!passwordPattern.matches(passwordPattern)){
            throw new IllegalArgumentException("Please enter a strong password");
        }

        String password = passwordEncoder.encode(newPassword);
        vendor.setPasswordHash(password);
        vendor.setPasswordChangeRequired(false);

        vendorRepository.save(vendor);

        return "Password changed successfully.";

    }

    //Update - Reset password

    public void resetPassword (String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email can not be empty.");
        }
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailPattern)) {
            throw new IllegalArgumentException("Please enter a valid email address.");
        }

        Optional<Vendor> result = vendorRepository.findByEmail(email);
        Vendor vendor;
        if (result.isPresent()) {
            vendor = result.get();
        } else {
            throw new IllegalArgumentException("No vendor account found for this email.");
        }

        if (!vendor.isActive()) {
            throw new IllegalArgumentException("Please activate your account first");
        }
        otpService.generateOTP(email);


    }


    public String confirmPasswordReset(String email, String submittedOtp, String newPassword) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailPattern)) {
            throw new IllegalArgumentException("Please enter a valid email address.");
        }
        if (submittedOtp == null || submittedOtp.isBlank()) {
            throw new IllegalArgumentException("OTP can not be empty");
        }

        if (newPassword == null || newPassword.isBlank()) {
            throw new IllegalArgumentException("Password is required.");
        }

        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$";
        if (!newPassword.matches(passwordPattern)) {
            throw new IllegalArgumentException("Password must be 8 characters.");
        }

        Optional<Vendor> result = vendorRepository.findByEmail(email);
        Vendor vendor;
        if (result.isPresent()) {
            vendor = result.get();
        } else {
            throw new IllegalArgumentException("No vendor account found for this email.");
        }
        otpService.verifyOtp(email, submittedOtp);
        String newPasswordHash = passwordEncoder.encode(newPassword);
        vendor.setPasswordHash(newPasswordHash);
        vendor.setPasswordChangeRequired(false);
        vendorRepository.save(vendor);

        return "Password reset successfully.Please login using your new password";

    }


}








