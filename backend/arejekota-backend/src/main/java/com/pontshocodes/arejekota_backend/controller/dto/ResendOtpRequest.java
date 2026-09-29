package com.pontshocodes.arejekota_backend.dto;

public class ResendOtpRequest {
    private String email;

    public void setEmail(String email){
        this.email=email;
    }
    public String getEmail(){
        return this.email;
    }
}
