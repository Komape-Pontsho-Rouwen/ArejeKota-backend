package com.pontshocodes.arejekota_backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="vendor_id",nullable=false)
    private Vendor vendor;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private EmployeeRole role;

    @Column(name="employee_id", nullable=false)
    private String employeeId;

    @Column(name="first_name",nullable=false)
    private String firstName;

    @Column(name="last_name", nullable=false)
    private String lastName;

    @Column(name="email",nullable =false , unique=true)
    private  String email;

    @Column(name="password_hash",nullable=false)
    private String passwordHash;

    @Column(nullable=false)
    private boolean active;

    @Column(name="created_at",nullable=false)
    private LocalDateTime createdAt;

    @Column (name="password_change_required",nullable=false)
    private boolean passwordChangeRequired;

    public Employee(){}

    public Employee(String employeeId,String firstName ,String lastName,String email ,String passwordHash,EmployeeRole role , Vendor vendor){
        this.employeeId=employeeId;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.passwordHash=passwordHash;
        this.role=role;
        this.vendor=vendor;
        this.active=true;
        this.passwordChangeRequired=true;
        this.createdAt = LocalDateTime.now();
    }
    //Getters and Setters  for our field

    public Long getId(){return id;}
    public String getEmployeeId(){return employeeId;}

    public String getFirstName(){return firstName;}
    public void setFirstName(String firstName){this.firstName=firstName;}
    public String getLastName(){return lastName;}
    public void setLastName(String lastName){this.lastName=lastName;}
    public String getEmail(){return email;}

    public String getPasswordHash(){return passwordHash;}
    public void setPasswordHash(String passwordHash){this.passwordHash = passwordHash;}
    public EmployeeRole getRole(){return role;}
    public boolean isActive(){return active;}
    public void setActive(boolean active){this.active=active;}
    public boolean isPasswordChangeRequired(){return passwordChangeRequired;}
    public void setPasswordChangeRequired(boolean passwordChangeRequired){this.passwordChangeRequired=passwordChangeRequired;}
    public LocalDateTime getCreatedAt(){return createdAt;}
    public Vendor getVendor(){return vendor;}
}


