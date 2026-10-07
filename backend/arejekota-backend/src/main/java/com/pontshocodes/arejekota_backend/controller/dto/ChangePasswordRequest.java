package com.pontshocodes.arejekota_backend.controller.dto;

public class ChangePasswordRequest {

    private String workEmail;
    private String newPassword;

    public void setWorkEmail(String workEmail){this.workEmail = workEmail;}
    public String getWorkEmail(){return workEmail;}

    public void setNewPassword(String newPassword){this.newPassword=newPassword;}
    public String getNewPassword(){return newPassword;}
}
