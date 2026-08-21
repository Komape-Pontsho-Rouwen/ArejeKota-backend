package com.pontshocodes.arejekota_backend.entity;

import com.pontshocodes.arejekota_backend.repository.CustomerRepository;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name ="customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name="first_name" , nullable=false)
    private String firstName;
    @Column(name ="last_name", nullable=false)
    private String lastName;
    @Column(nullable=false , unique=true)
    private String email;
    @Column(name="password_hash",nullable=false)
    private String passwordHash;
    @Column(name="created_at",nullable=false)
    private LocalDateTime createdAt;//Will later use by crypt password encoder
    @Column(name="is_activate",nullable= false)
    private boolean active=false;//account start inactive until OTP is verified

    public Customer(){}

    public Customer(String firstName, String lastName, String email, String passwordHash){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = LocalDateTime.now();
        this.active = false;
    }
    //Getters for our fields
    public Long getId(){
        return this.id;
    }
    public String getFirstName(){
        return this.firstName;
    }
    public String getLastName(){
        return this.lastName;
    }
    public String getEmail(){
        return this.email;
    }
    public String getPasswordHash(){
        return this.passwordHash;
    }
    public LocalDateTime getCreatedAt(){
        return this.createdAt;
    }
    public boolean isActive(){return this.active;}

    //Setters for our fields
    public void setId(Long Id){
        this.id = Id;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPasswordHash(String passwordHash){
        this.passwordHash = passwordHash;
    }
    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }
    public void setActive(boolean active){this.active = active;
    }
}


