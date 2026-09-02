package com.pontshocodes.arejekota_backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Vendor {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="business_name" , nullable=false)
    private String businessName;

    @Column(name="address",nullable = false)
    private String address;

    @Column(name="email",nullable=false)
    private String email;

    @Column(name="passwordHash",nullable=false)
    private String passwordHash;

    @Column(name="active")
    private boolean active;

    @Column(name="createdAt")
    private LocalDateTime createdAt;

    public Vendor(){}

    public Vendor(String businessName ,String address ,String email ,String passwordHash){
        this.businessName=businessName;
        this.address=address;
        this.email=email;
        this.passwordHash=passwordHash;
        this.active = true;
        this.createdAt = LocalDateTime.now();

    }
    public void setId(long id){
        this.id = id;
    }
    public Long getId(){return id;}


    public void setBusinessName(String businessName){
        this.businessName = businessName;
    }
    public String getBusinessName(){return businessName;}

    public void setAddress(String address){
        this.address=address;
    }
    public String getAddress(){return address;}

    public void setEmail(String email){
        this.email= email;
    }
    public String getEmail(){return email;}

    public void setPasswordHash(String passwordHash){
        this.passwordHash=passwordHash;
    }
    public String getPasswordHash(){return passwordHash;}
    public boolean isActive(){return active;}



}


