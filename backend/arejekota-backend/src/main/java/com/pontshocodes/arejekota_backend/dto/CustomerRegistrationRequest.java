package com.pontshocodes.arejekota_backend.dto;

public class CustomerRegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    //setters and getters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFirstName(){
        return this.firstName ;
    }
    public String getLastName(){
        return this.lastName = lastName;
    }
    public String getEmail(){
        return this.email;
    }
    public String getPassword(){
        return this.password ;
    }

}
