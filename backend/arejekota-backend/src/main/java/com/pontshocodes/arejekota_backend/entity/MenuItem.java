package com.pontshocodes.arejekota_backend.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "menu_items")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable=false ,length=50)
    private String category;
    
    @Column(name = "image_url")
    private String imageUrl;

    @Column(nullable = false)
    private boolean available = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public MenuItem() {}

    public MenuItem(String name, String description, BigDecimal price,String category, String imageUrl, Restaurant restaurant) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
        this.restaurant = restaurant;
        this.available = true;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    public void setDescription(String description) { this.description = description; }
    public String getDescription() { return description; }

    public void setPrice(BigDecimal price) { this.price = price; }
    public BigDecimal getPrice() { return price; }

    public void setCategory(String category){ this.category=category;}
    public String getCategory(){return category;}
    
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getImageUrl() { return imageUrl; }

    public void setAvailable(boolean available) { this.available = available; }
    public boolean isAvailable() { return available; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }
    public Restaurant getRestaurant() { return restaurant; }


}