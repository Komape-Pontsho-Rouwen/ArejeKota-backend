package com.pontshocodes.arejekota_backend.dto;

public class UpdateCustomerRequest {
    private String email;
    private String newFirstName;
    private String newLastName;


    public void setEmail(String email){
        this.email = email;
    }
    public void setNewFirstName(String newFirstName){
        this.newFirstName=newFirstName;
    }
    public void setNewLastName(String lastName){
        this.newLastName = newLastName;
    }
    public String getEmail(){
        return this.email;
    }
    public String getNewFirstName(){
        return this.newFirstName;
    }
    public String getNewLastName(){
        return this.newLastName;
    }
}
