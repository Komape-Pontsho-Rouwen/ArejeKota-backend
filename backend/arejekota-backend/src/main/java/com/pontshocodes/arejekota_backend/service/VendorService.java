package com.pontshocodes.arejekota_backend.service;


import com.pontshocodes.arejekota_backend.entity.Vendor;
import com.pontshocodes.arejekota_backend.entity.VendorStatus;
import com.pontshocodes.arejekota_backend.exeption.EmailAlreadyExistsException;
import com.pontshocodes.arejekota_backend.repository.VendorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    private static final String EMAIL_DOMAIN ="@arejekota.co.za";
    private static final String PASSWORD_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz23456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public VendorService(VendorRepository vendorRepository ,PasswordEncoder passwordEncoder ,JwtService jwtService){
        this.vendorRepository=vendorRepository;
        this.passwordEncoder= passwordEncoder;
        this.jwtService=jwtService;

    }

    public Vendor register(String firstName,String lastName,String email,String cellPhone,String businessName ,String address){
        if(firstName==null || firstName.isBlank()){
            throw new IllegalArgumentException("First name can not be empty");

        }
        if(lastName==null || lastName.isBlank()){
            throw new IllegalArgumentException("Last name can not be empty");

        }
        if(email==null || email.isBlank()){
            throw new IllegalArgumentException("Email can not be empty");
        }
        String emailPattern ="^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if(!email.matches(emailPattern)){
            throw new IllegalArgumentException("Please enter a valid email");
        }
        if(cellPhone==null || cellPhone.isBlank()){
            throw new IllegalArgumentException("Cellphone can not be empty");

        }
        if(businessName==null || businessName.isBlank()){
            throw new IllegalArgumentException("Business name can not be empty");
        }
        if(address==null || address.isBlank()){
            throw new IllegalArgumentException("Address can not be empty");
        }
        if(vendorRepository.existsByEmail(email)){
            throw new EmailAlreadyExistsException("A vendor application already exists for this email");
        }
        Vendor vendor = new Vendor(firstName,lastName,email,cellPhone,businessName,address);
        vendorRepository.save(vendor);

        return vendor;
    }

    public Vendor approveVendor(Long vendorId) {
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
        return vendor;
    }

    private String generateVendorWorkEmail(String businessName){
        String base = businessName.trim().toLowerCase().replaceAll("[^a-z0-9]" , "");
        int suffix = 1;
        String candidate;
        do{
            candidate = base + String.format("%02d",suffix) + EMAIL_DOMAIN;
            suffix ++;

        }while (vendorRepository.existsByWorkEmail(candidate));
        return candidate;
    }

    private String generateTemporaryPassword() {
        StringBuilder passwordBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            passwordBuilder.append(PASSWORD_CHARS.charAt(RANDOM.nextInt(PASSWORD_CHARS.length())));
        }
        return passwordBuilder.toString();

    }
    //Vendor Login

    public Vendor rejectVendor(Long vendorId){
        Optional<Vendor> result = vendorRepository.findById(vendorId);
        Vendor vendor;
        if(result.isPresent()){
            vendor = result.get();
        }
        else{
            throw new IllegalArgumentException("No vendor application found for this id");
        }
        if(vendor.getStatus() != VendorStatus.PENDING){
            throw new IllegalArgumentException("Onl pending vendors can be rejected");
        }
        vendor.setStatus(VendorStatus.REJECTED);
        vendorRepository.save(vendor);
        return vendor;
    }
    public List<Vendor> listPendingVendors(){
        return vendorRepository.findByStatus(VendorStatus.PENDING);
    }


    public List<Vendor> listRejectedVendors(){return vendorRepository.findByStatus(VendorStatus.REJECTED);}

    public String login(String workEmail , String password) {
        if (workEmail == null || workEmail.isBlank()) {
            throw new IllegalArgumentException("Email can not be empty");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password can not be empty");
        }
        Vendor vendor = vendorRepository.findByWorkEmail(workEmail)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email"));
        if (passwordEncoder.matches(password, vendor.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid Password");
        }

        return jwtService.generateToken(vendor.getWorkEmail() ,"VENDOR");
    }
    public String changePassword(String workEmail , String newPassword){
        if (workEmail == null || workEmail.isBlank()) {
            throw new IllegalArgumentException("Email can not be empty");
        }
        if (newPassword == null || newPassword.isBlank()) {
            throw new IllegalArgumentException("Password can not be empty");
        }
        Vendor vendor = vendorRepository.findByWorkEmail(workEmail).orElseThrow(() -> new IllegalArgumentException("Vendor not found"));


        String password = passwordEncoder.encode(newPassword);
        vendor.setPasswordHash(password);
        vendor.setPasswordChangeRequired(false);

        vendorRepository.save(vendor);

        return "Password changed successfully";

    }

}





