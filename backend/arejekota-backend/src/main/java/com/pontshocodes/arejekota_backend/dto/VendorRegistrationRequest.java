package com.pontshocodes.arejekota_backend.dto;

public class VendorRegistrationRequest {
    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;
    public String businessName;
    public String address;


    public void setFirstName(String firstName){this.firstName=firstName;}
    public String getFirstName(){return firstName;}

    public void setLastName(String lastName){this.lastName=lastName;}
    public String getLastName(){return lastName;}

    public void setEmail(String email){this.email =email;}
    public String getEmail(){return email;}

    public void setPhoneNumber(String phoneNumber){this.phoneNumber = phoneNumber;}
    public String getPhoneNumber(){return phoneNumber;}

    public void setBusinessName(String businessName){this.businessName = businessName;}
    public String  getBusinessName(){return businessName;}

    public void setAddress(String address){this.address= address;}
    public String getAddress(){return address;}

}
