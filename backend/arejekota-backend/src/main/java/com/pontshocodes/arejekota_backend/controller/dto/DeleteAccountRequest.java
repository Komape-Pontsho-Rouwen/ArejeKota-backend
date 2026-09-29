package com.pontshocodes.arejekota_backend.dto;

public class DeleteAccountRequest {
    private String email;
    private String submittedCode;

    public void setEmail(String email){
        this.email = email;
    }
    public  void setSubmittedCode(String submittedCode){
        this.submittedCode = submittedCode;
    }
    public String getEmail(){
        return this.email;
    }
    public String getSubmittedCode(){
        return this.submittedCode;
    }
}
