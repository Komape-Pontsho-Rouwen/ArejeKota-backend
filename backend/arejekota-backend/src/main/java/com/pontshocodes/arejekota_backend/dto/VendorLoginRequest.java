package com.pontshocodes.arejekota_backend.dto;

public class VendorLoginRequest {
    String workEmail;
    String password;

    public void setWorkEmail(String workEmail){ this.workEmail = workEmail;}
    public String getWorkEmail(){return workEmail;}

    public void setPassword(String password){this.password= password;}
    public String getPassword(){return password;}


}
