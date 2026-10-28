package com.pontshocodes.arejekota_backend.dto;

public class ConfirmPasswordResetRequest {
    private String email;
    private String submittedCode;
    private String newPassword;

    public void setEmail(String email){
        this.email = email;
    }
    public void setSubmittedCode(String submittedCode){
        this.submittedCode=submittedCode;
    }
    public void setPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    public String getEmail(){
        return this.email;
    }
    public String getSubmittedCode(){
        return this.submittedCode;
    }
    public String getNewPassword(){
        return this.newPassword;
    }
}

