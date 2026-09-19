package com.pontshocodes.arejekota_backend.service;


import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class PasswordGenerator {

    private static final String PASSWORD_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz23456789";
    private static final SecureRandom RANDOM = new SecureRandom();


    public String generateTemporaryPassword() {
        StringBuilder passwordBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            passwordBuilder.append(PASSWORD_CHARS.charAt(RANDOM.nextInt(PASSWORD_CHARS.length())));
        }
        ;
        return passwordBuilder.toString();


    }
}
