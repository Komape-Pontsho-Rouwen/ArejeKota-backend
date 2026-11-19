package com.pontshocodes.arejekota_backend.dto;

import java.time.LocalTime;

public class RestaurantResponse {

    private Long id;
    private String name;
    private String description;
    private String address;
    private String phoneNumber;
    private String imageUrl;
    private LocalTime openingTime;
    private LocalTime closingTime;

    public RestaurantResponse(Long id, String name, String description, String address,
                              String phoneNumber, String imageUrl,
                              LocalTime openingTime, LocalTime closingTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.imageUrl = imageUrl;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getImageUrl() { return imageUrl; }
    public LocalTime getOpeningTime() { return openingTime; }
    public LocalTime getClosingTime() { return closingTime; }
}