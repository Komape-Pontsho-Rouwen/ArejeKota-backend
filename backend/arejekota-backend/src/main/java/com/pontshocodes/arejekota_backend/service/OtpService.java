package com.pontshocodes.arejekota_backend.service;

import com.pontshocodes.arejekota_backend.entity.OtpCode;
import com.pontshocodes.arejekota_backend.repository.OtpCodeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
@Service
public class OtpService {

    private final OtpCodeRepository otpRepository;

    public OtpService(OtpCodeRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    public String generateOTP(String email) {
        //Deletes unused  otp from the database before generating a new one
        otpRepository.deleteByEmail(email);
        String code = String.valueOf(new Random().nextInt(900000) + 100000);
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(10);
        //Create the actual OTP with generated arguments
        OtpCode otpCode = new OtpCode(email, code, expiresAt);
        otpRepository.save(otpCode);

        return code;

    }

    public boolean verifyOtp(String email , String submittedCode) {
        if (submittedCode == null || submittedCode.isBlank()) {
            throw new IllegalArgumentException("OTP can not be null");
        }
        //Local variable for OTP
        OtpCode otpCode = otpRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("No OTP found for this email."));
        if(LocalDateTime.now().isAfter(otpCode.getExpiresAt())){
            otpRepository.deleteByEmail(email);
            throw new IllegalArgumentException("OTP has expired");
        }
        if (!submittedCode.equals(otpCode.getCode())){
            throw new IllegalArgumentException("Incorrect OTP");
        }
        otpRepository.deleteByEmail(email);
        return true;

    }

}
