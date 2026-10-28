package com.pontshocodes.arejekota_backend.dto;

public class PendingVendorResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String cellPhone;
    private String businessName;
    private String address;

    public PendingVendorResponse(Long id, String firstName, String lastName, String email,
                                 String cellPhone, String businessName, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.cellPhone = cellPhone;
        this.businessName = businessName;
        this.address = address;
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getCellPhone() { return cellPhone; }
    public String getBusinessName() { return businessName; }
    public String getAddress() { return address; }
}


