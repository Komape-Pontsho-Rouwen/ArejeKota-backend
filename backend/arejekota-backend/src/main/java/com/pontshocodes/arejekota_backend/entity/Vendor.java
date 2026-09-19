package com.pontshocodes.arejekota_backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="vendors")
public class Vendor {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name",nullable=false)
    private String firstName;

    @Column(name="last_name",nullable=false)
    private String lastName;

    @Column(name="email",nullable=false)
    private String email;

    @Column(name="cellphone",nullable=false)
    private String cellPhone;

    @Column(name="business_name" , nullable=false)
    private String businessName;

    @Column(name="address",nullable = false)
    private String address;

    @Column(name="submitted_at",nullable=false)
    private LocalDateTime submittedAt;

    @Enumerated(EnumType.STRING)
    @Column(name="status",nullable=false)
    private VendorStatus status;

    @Column(name="approved_at",nullable = false)
    private LocalDateTime approvedAt;

    @Column(name="password_change_required",nullable = false)
    private boolean passwordChangeRequired;

    @Column(name="password_hash")
    private String passwordHash;

    @Column(name="active",nullable = false)
    private boolean active;

    @Column(name="work_email")
    private String workEmail;



    public Vendor(){}

    public Vendor(String firstName ,String lastName ,String email ,String  cellPhone,String businessName ,String address ){
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.cellPhone = cellPhone;
        this.businessName=businessName;
        this.address=address;
        this.active=false;
        this.status = VendorStatus.PENDING;
        this.passwordChangeRequired= false;


    }
    public void setId(long id){
        this.id = id;
    }
    public Long getId(){return id;}

    public void setFirstName(String firstName){this.firstName=firstName;}
    public String getFirstName(){return firstName;}

    public void setLastName(String lastName){this.lastName=lastName;}
    public String getLastName(){return lastName;}

    public void setCellPhone(String cellPhone){this.cellPhone=cellPhone;}
    public String getCellPhone(){return cellPhone;}

    public void setEmail(String email){
        this.email= email;
    }
    public String getEmail(){return email;}


    public void setBusinessName(String businessName){
        this.businessName = businessName;
    }
    public String getBusinessName(){return businessName;}

    public void setAddress(String address){
        this.address=address;
    }
    public String getAddress(){return address;}

    public LocalDateTime getSubmittedAt(){return submittedAt;}

    public void setStatus(VendorStatus status){this.status=status;}
    public VendorStatus getStatus(){return status;}

    public void setApprovedAt(LocalDateTime approvedAt){this.approvedAt=approvedAt;}
    public LocalDateTime getApprovedAt(){return approvedAt;}

    public void setPasswordChangeRequired(boolean passwordChangeRequired){this.passwordChangeRequired=passwordChangeRequired;}
    public boolean isPasswordChangeRequired(){return passwordChangeRequired;}


    public void setPasswordHash(String passwordHash){
        this.passwordHash=passwordHash;
    }
    public String getPasswordHash(){return passwordHash;}

    public void setActive(boolean active){this.active=active;}
    public boolean isActive(){return active;}

    public void setWorkEmail(String workEmail){this.workEmail = workEmail;}
    public String getWorkEmail(){return workEmail;}



}


