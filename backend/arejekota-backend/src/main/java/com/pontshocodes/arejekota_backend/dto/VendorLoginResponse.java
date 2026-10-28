package com.pontshocodes.arejekota_backend.dto;

public class VendorLoginResponse {
    private String token;
    private boolean passwordChangeRequired;

    public VendorLoginResponse(String token ,boolean passwordChangeRequired){
        this.token = token;
        this.passwordChangeRequired = passwordChangeRequired;
    }
    public String getToken(){return token;}
    public boolean isPasswordChangeRequired(){ return passwordChangeRequired;}
}
