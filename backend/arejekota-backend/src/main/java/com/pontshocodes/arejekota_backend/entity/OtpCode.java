package com.pontshocodes.arejekota_backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name="otp_codes")
public class OtpCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




    @OneToOne
    @JoinColumn(name="customer_id",nullable=false,unique=true)
    private Customer customer;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private int attempts = 0;

    protected OtpCode() {
    }

    public OtpCode(Customer customer,String code, LocalDateTime expiresAt) {
        this.customer = customer;
        this.code = code;
        this.expiresAt = expiresAt;
    }



    public Long getId() { return id; }
    public Customer getCustomer() { return customer; }
    public String getCode() { return code; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public int getAttempts(){return attempts;}
    public void setAttempts(int attempts){
        this.attempts = attempts;
    }
}


