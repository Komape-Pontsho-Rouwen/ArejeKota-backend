package com.pontshocodes.arejekota_backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name="otp_codes")
public class OtpCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false , unique=true)
    private String email;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private int attempts = 0;

    protected OtpCode() {
    }

    public OtpCode(String email,String code, LocalDateTime expiresAt) {
        this.email= email;
        this.code = code;
        this.expiresAt = expiresAt;
    }

    public String getEmail(){return email;}
    public Long getId() { return id; }
    public String getCode() { return code; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public int getAttempts(){return attempts;}
    public void setAttempts(int attempts){
        this.attempts = attempts;
    }
}


