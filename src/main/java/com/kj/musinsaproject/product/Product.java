package com.kj.musinsaproject.product;

import com.kj.musinsaproject.brand.Brand;
import com.kj.musinsaproject.category.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private Double price;

    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime addDate;

    @PrePersist
    protected void onCreate() {
        addDate = LocalDateTime.now();
    }
}
