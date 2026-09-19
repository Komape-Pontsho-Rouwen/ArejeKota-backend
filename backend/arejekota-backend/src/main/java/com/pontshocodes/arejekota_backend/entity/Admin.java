package com.pontshocodes.arejekota_backend.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="email",nullable = false ,unique =true , updatable = false)
    private String email;

    @Column(name="password_hash",nullable =false)
    private String passwordHash;

    @Column(name="active")
    private boolean active;

    @Column(name ="created_at" ,nullable = false)
    private LocalDateTime createdAt;

    public Admin(){}

    public Admin(String email , String passwordHash){
        this.email = email;
        this.passwordHash=passwordHash;
        this.active = true;
        this.createdAt = LocalDateTime.now();
    }
    public Long getId(){return id;}

    public void setEmail(String email){this.email=email;}
    public String getEmail(){return email;}

    public void setPasswordHash(String passwordHash){this.passwordHash=passwordHash;}
    public String getPasswordHash(){return passwordHash;}

    public void setActive(boolean active){this.active =active;}
    public boolean isActive(){return active;}

    public void setCreatedAt(LocalDateTime createdAt){this.createdAt=createdAt;}
    public LocalDateTime getCreatedAt(){return createdAt;}



}
