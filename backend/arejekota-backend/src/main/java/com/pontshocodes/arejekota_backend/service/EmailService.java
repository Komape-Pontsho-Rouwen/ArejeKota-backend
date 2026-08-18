package com.pontshocodes.arejekota_backend.service;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }
    public void sendOtpEmail(String toEmail ,String code){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("pontshotizzy1@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Your ArejeKota Verification Code");
        message.setText("Your OTP code is:" +code+ "\n\n " +
                "This code expires in 10 minutes.\n\n If you did not request this," +
                "ignore this email");
        mailSender.send(message);

    }

}
