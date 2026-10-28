package com.pontshocodes.arejekota_backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name="restaurant")
public class Restaurant{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, length = 100)
        private String name;

        @Column(length = 500)
        private String description;

        @Column(nullable = false, length = 255)
        private String address;

        @Column(name = "phone_number", nullable = false, length = 20)
        private String phoneNumber;

        // Only the link is stored. The image file itself lives in the uploads folder.
        @Column(name = "image_url", nullable = false)
        private String imageUrl;

        @Column(name = "opening_time")
        private LocalTime openingTime;

        @Column(name = "closing_time")
        private LocalTime closingTime;

        @Column(nullable = false)
        private boolean active = true;

        @Column(name = "created_at", nullable = false)
        private LocalDateTime createdAt;

        @OneToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "vendor_id", nullable = false, unique = true)
        private Vendor vendor;

        public Restaurant() {}

        public Restaurant(String name, String description, String address, String phoneNumber,
                          String imageUrl, LocalTime openingTime, LocalTime closingTime, Vendor vendor) {
            this.name = name;
            this.description = description;
            this.address = address;
            this.phoneNumber = phoneNumber;
            this.imageUrl = imageUrl;
            this.openingTime = openingTime;
            this.closingTime = closingTime;
            this.vendor = vendor;
            this.createdAt = LocalDateTime.now();
        }

        public Long getId() { return id; }

        public void setName(String name) { this.name = name; }
        public String getName() { return name; }

        public void setDescription(String description) { this.description = description; }
        public String getDescription() { return description; }

        public void setAddress(String address) { this.address = address; }
        public String getAddress() { return address; }

        public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
        public String getPhoneNumber() { return phoneNumber; }

        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
        public String getImageUrl() { return imageUrl; }

        public void setOpeningTime(LocalTime openingTime) { this.openingTime = openingTime; }
        public LocalTime getOpeningTime() { return openingTime; }

        public void setClosingTime(LocalTime closingTime) { this.closingTime = closingTime; }
        public LocalTime getClosingTime() { return closingTime; }

        public void setActive(boolean active) { this.active = active; }
        public boolean isActive() { return active; }

        public LocalDateTime getCreatedAt() { return createdAt; }

        public void setVendor(Vendor vendor) { this.vendor = vendor; }
        public Vendor getVendor() { return vendor; }
   }

