package com.pontshocodes.arejekota_backend.dto;


import java.math.BigDecimal;

public class MenuItemResponse {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private String category;
    private boolean available;

    

    public MenuItemResponse(Long id, String name, String description, BigDecimal price,
                            String imageUrl, String category, boolean available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.available = available;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public String getCategory() { return category; }
    public boolean isAvailable() { return available; }
}


