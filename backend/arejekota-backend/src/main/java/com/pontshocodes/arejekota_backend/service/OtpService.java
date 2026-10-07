package com.pontshocodes.arejekota_backend.service;

import com.pontshocodes.arejekota_backend.entity.Customer;
import com.pontshocodes.arejekota_backend.entity.OtpCode;
import com.pontshocodes.arejekota_backend.repository.OtpCodeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
@Service
public class OtpService {

    private final OtpCodeRepository otpRepository;
    private final EmailService emailService;

    public OtpService(OtpCodeRepository otpRepository, EmailService emailService) {
        this.otpRepository = otpRepository;
        this.emailService = emailService;
    }
    @Transactional
    public String generateOTP(String email) {
        //Deletes unused  otp from the database before generating a new one
        otpRepository.deleteByEmail(email);
        otpRepository.flush();//wasn't fully deleting the old otp
        String code = String.valueOf(new Random().nextInt(900000) + 100000);
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(30);
        //Create the actual OTP with generated arguments
        OtpCode otpCode = new OtpCode(email, code, expiresAt);
        otpRepository.save(otpCode);

        emailService.sendOtpEmail(email, code);

        return code;

    }


    public boolean verifyOtp(String email, String submittedCode) {
        if (submittedCode == null || submittedCode.isBlank()) {
            throw new IllegalArgumentException("OTP can not be null");
        }
        //Local variable for OTP
        OtpCode otpCode = otpRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("No OTP found for this email."));
        if (LocalDateTime.now().isAfter(otpCode.getExpiresAt())) {
            otpRepository.deleteByEmail(email);
            throw new IllegalArgumentException("OTP has expired");
        }
        if (!submittedCode.equals(otpCode.getCode())) {
            otpCode.setAttempts(otpCode.getAttempts() + 1);
            if (otpCode.getAttempts() >= 3) {
                otpRepository.deleteByEmail(email);
                throw new RuntimeException("Too mny failed attempts.Please request an new OTP");
            }
            otpRepository.save(otpCode);
            throw new IllegalArgumentException("Incorrect OTP");
        }
        otpRepository.deleteByEmail(email);
        return true;


    }

}
